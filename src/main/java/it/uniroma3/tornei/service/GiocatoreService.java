package it.uniroma3.tornei.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.exception.GiocatoreDuplicatoException;
import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.repository.GiocatoreRepository;
import it.uniroma3.tornei.repository.SquadraRepository;

@Service
@Transactional(readOnly = true)
public class GiocatoreService {
	
	private final GiocatoreRepository giocatoreRepository;
	private final SquadraRepository squadraRepository;
	
	public GiocatoreService(GiocatoreRepository giocatoreRepository, SquadraRepository squadraRepository) {
		this.giocatoreRepository = giocatoreRepository;
		this.squadraRepository = squadraRepository;
	}

	public Giocatore getGiocatore(Long id) {
		return this.giocatoreRepository.findById(id).orElse(null);
	}
	
	public List<Giocatore> getAllGiocatori() {
		return (List<Giocatore>) this.giocatoreRepository.findAll();
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Giocatore saveGiocatore(Giocatore giocatore) {
		
		boolean duplicato = giocatore.getId() == null
				? this.giocatoreRepository.existsByNomeAndCognomeAndDataNascitaAndRuolo(
						giocatore.getNome(), giocatore.getCognome(), giocatore.getDataNascita(), giocatore.getRuolo())
				: this.giocatoreRepository.existsByNomeAndCognomeAndDataNascitaAndRuoloAndIdNot(
						giocatore.getNome(), giocatore.getCognome(), giocatore.getDataNascita(), giocatore.getRuolo(), giocatore.getId());
		
		if(duplicato)
			throw new GiocatoreDuplicatoException(giocatore.getNome(), giocatore.getCognome());
		
		return this.giocatoreRepository.save(giocatore);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Giocatore updateGiocatore(Long id, Giocatore datiAggiornati) {
		
		Giocatore originale = this.giocatoreRepository.findById(id).orElse(null);
		if (originale == null) {
			return null;
		}

		boolean duplicato = this.giocatoreRepository.existsByNomeAndCognomeAndDataNascitaAndRuoloAndIdNot(
				datiAggiornati.getNome(), datiAggiornati.getCognome(), datiAggiornati.getDataNascita(), datiAggiornati.getRuolo(), id);
		if (duplicato) {
			throw new GiocatoreDuplicatoException(datiAggiornati.getNome(), datiAggiornati.getCognome());
		}

		if (!originale.getSquadra().getId().equals(datiAggiornati.getSquadra().getId())) {
			
			//rimuoviamo il giocatore dalla vecchia squadra
			Squadra vecchiaSquadra = originale.getSquadra();
			vecchiaSquadra.removeGiocatore(originale);
			
			//carichiamo la nuova squadra dal database e vi aggiungiamo il giocatore
			Squadra nuovaSquadra = this.squadraRepository.findById(datiAggiornati.getSquadra().getId()).orElse(null);
			if (nuovaSquadra != null) {
				nuovaSquadra.addGiocatore(originale);
				originale.setSquadra(nuovaSquadra);
			}
		}

		originale.setNome(datiAggiornati.getNome());
		originale.setCognome(datiAggiornati.getCognome());
		originale.setDataNascita(datiAggiornati.getDataNascita());
		originale.setRuolo(datiAggiornati.getRuolo());
		originale.setAltezza(datiAggiornati.getAltezza());
		originale.setFotoUrl(datiAggiornati.getFotoUrl());

		return this.giocatoreRepository.save(originale);
	}
}
