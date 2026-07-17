package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.exception.ModificaAnnoTorneoException;
import it.uniroma3.tornei.exception.TorneoDuplicatoException;
import it.uniroma3.tornei.exception.TorneoInUsoException;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.RigaClassifica;
import it.uniroma3.tornei.model.StatoPartita;
import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.repository.TorneoRepository;

@Service
@Transactional(readOnly = true)
public class TorneoService {
	
	private final TorneoRepository torneoRepository;
	
	public TorneoService(TorneoRepository torneoRepository) {
		this.torneoRepository = torneoRepository;
	}
	
	public Torneo getTorneo(Long id) {
		return this.torneoRepository.findById(id).orElse(null);
	}
	
	public List<Torneo> getAllTornei() {
		return (List<Torneo>) this.torneoRepository.findAll();
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Torneo saveTorneo(Torneo torneo) {
		
		if(this.torneoRepository.existsByNomeAndAnno(torneo.getNome(), torneo.getAnno()))
			throw new TorneoDuplicatoException(torneo.getNome(), torneo.getAnno());
		
		return this.torneoRepository.save(torneo);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteTorneo(Long id) {
		
		Torneo torneo = this.torneoRepository.findByIdWithPartiteAndSquadre(id).orElse(null);
		if(torneo != null) {
			boolean haPartiteGiocate = torneo.getPartite().stream().anyMatch(
					p -> p.getStato().equals(StatoPartita.TERMINATA));
			if(haPartiteGiocate)
				throw new TorneoInUsoException();
			
			this.torneoRepository.delete(torneo);
		}
	}
	
	//non ha bisogno di un transactional più specifico perchè non modifica lo stato ma fa solo tante letture
	public List<RigaClassifica> calcolaClassifica(Long torneoId) {
		
		Torneo torneo = this.torneoRepository.findByIdWithPartiteAndSquadre(torneoId).orElse(null);
		if(torneo == null)
			return new ArrayList<>();
		
		Map<String, RigaClassifica> mappaClassifica = new HashMap<>();
		
		for (Partita p : torneo.getPartite()) {
			
			if(p.getStato().equals(StatoPartita.TERMINATA)) {
				
				//prendo i nomi delle squadre in questione
				String casa = (p.getSquadraCasa() != null) ? p.getSquadraCasa().getNome() : p.getSquadraCasaNomeStorico();
				String ospite = (p.getSquadraOspite() != null) ? p.getSquadraOspite().getNome() : p.getSquadraOspiteNomeStorico();
				
				//metto le squadre nella mappa delle classifiche (solo se non sono già presenti)
				mappaClassifica.putIfAbsent(casa, new RigaClassifica(casa));
				mappaClassifica.putIfAbsent(ospite, new RigaClassifica(ospite));
				
				//estraggo le riga di classifica delle squadre
				RigaClassifica rigaCasa = mappaClassifica.get(casa);
				RigaClassifica rigaOspite = mappaClassifica.get(ospite);
				
				//verifica l'eventuale eliminazione della squadra
				if(p.getSquadraCasa() == null)
					rigaCasa.setRitirata(true);
				if(p.getSquadraOspite() == null)
					rigaOspite.setRitirata(true);
				
				//recupero i gol
				Integer gHome = p.getGoalsHome();
				Integer gAway = p.getGoalsAway();
				
				/* calcolo punteggi */
				if(gHome > gAway) {
					rigaCasa.aggiungiPartita(gHome, gAway, 3);
					rigaOspite.aggiungiPartita(gAway, gHome, 0);
				} else if(gAway > gHome) {
					rigaCasa.aggiungiPartita(gHome, gAway, 0);
					rigaOspite.aggiungiPartita(gAway, gHome, 3);
				} else {
					//pareggio
					rigaCasa.aggiungiPartita(gHome, gAway, 1);
					rigaOspite.aggiungiPartita(gAway, gHome, 1);
				}
			}
		}
		
		//metto i valori della mappa in una lista ordinabile
		List<RigaClassifica> classificaOrdinata = new ArrayList<>(mappaClassifica.values());
		
		Collections.sort(classificaOrdinata);
		
		return classificaOrdinata;
	}
	
	// Metodo di supporto per aggiornare un torneo esistente validando il cambio d'anno
	@Transactional(isolation = Isolation.READ_COMMITTED)
    public Torneo updateTorneo(Long id, Torneo datiAggiornati) {
		
        Torneo originale = this.torneoRepository.findById(id).orElse(null);
            
        if (this.torneoRepository.existsByNomeAndAnnoAndIdNot(datiAggiornati.getNome(), datiAggiornati.getAnno(), id)) {
            throw new TorneoDuplicatoException(originale.getNome(), originale.getAnno());
        }

        if (!originale.getAnno().equals(datiAggiornati.getAnno())) {
            if (originale.getPartite() != null && !originale.getPartite().isEmpty()) {
                throw new ModificaAnnoTorneoException();
            }
            
            originale.setAnno(datiAggiornati.getAnno());
        }
        
        originale.setNome(datiAggiornati.getNome());
        originale.setDescrizione(datiAggiornati.getDescrizione());
        
        return this.torneoRepository.save(originale);
    }
}


