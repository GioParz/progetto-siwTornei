package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
		
		Optional<Torneo> result = this.torneoRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Torneo> getAllTornei() {
		
		List<Torneo> tornei = new ArrayList<>();
		//aggiungiamo ogni elemento restituito da findAll dentro tornei
		this.torneoRepository.findAll().forEach(tornei::add);
		
		return tornei;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Torneo saveTorneo(Torneo torneo) {
		return this.torneoRepository.save(torneo);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteTorneo(Long id) {
		//la cancellazione per id è più efficiente in quanto non carica tutto l'oggetto in memoria prima di cancellarlo
		this.torneoRepository.deleteById(id);
	}
	
	//non ha bisogno di un transactional più specifico perchè non modifica lo stato ma fa solo tante letture
	public List<RigaClassifica> calcolaClassifica(Long torneoId) {
		
		Torneo torneo = this.torneoRepository.findById(torneoId).orElse(null);
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
		
		classificaOrdinata.sort((a, b) -> {
			//criterio 1: ordinamento in casi di squadra ritirata(quest'ultima ha la minima priorità)
			if(a.isRitirata() && !b.isRitirata()) return 1; //a dopo di b
			if(!a.isRitirata() && b.isRitirata()) return -1; //a prima di b
			
			//criterio 2: ordinate per punti
			if(b.getPunti() != a.getPunti())
				return Integer.compare(b.getPunti(), a.getPunti()); //ordine decrescente punti
			
			//criterio 3: differenza reti in casi di parità
			Integer diffRetiB = b.getGolFatti() - b.getGolSubiti();
			Integer diffRetiA = a.getGolFatti() - a.getGolSubiti();
			return Integer.compare(diffRetiB, diffRetiA);
		});
		
		return classificaOrdinata;
	}
}
