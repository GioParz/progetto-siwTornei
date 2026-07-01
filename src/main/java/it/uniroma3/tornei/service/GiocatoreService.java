package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.repository.GiocatoreRepository;

@Service
public class GiocatoreService {
	
	@Autowired
	private GiocatoreRepository giocatoreRepository;
	
	public Giocatore getGiocatore(Long id) {
		
		Optional<Giocatore> result = this.giocatoreRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Giocatore> getAllGiocatori() {
		
		List<Giocatore> giocatori = new ArrayList<>();
		this.giocatoreRepository.findAll().forEach(giocatori::add);
		
		return giocatori;
	}
	
	@Transactional
	public Giocatore saveGiocatore(Giocatore giocatore) {
		
		return this.giocatoreRepository.save(giocatore);
	}
}
