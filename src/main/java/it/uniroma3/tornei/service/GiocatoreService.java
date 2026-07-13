package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.repository.GiocatoreRepository;

@Service
@Transactional(readOnly = true)
public class GiocatoreService {
	
	private final GiocatoreRepository giocatoreRepository;
	
	public GiocatoreService(GiocatoreRepository giocatoreRepository) {
		this.giocatoreRepository = giocatoreRepository;
	}

	public Giocatore getGiocatore(Long id) {
		
		Optional<Giocatore> result = this.giocatoreRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Giocatore> getAllGiocatori() {
		
		List<Giocatore> giocatori = new ArrayList<>();
		this.giocatoreRepository.findAll().forEach(giocatori::add);
		
		return giocatori;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Giocatore saveGiocatore(Giocatore giocatore) {
		return this.giocatoreRepository.save(giocatore);
	}
}
