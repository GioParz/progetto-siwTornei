package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Utente;
import it.uniroma3.tornei.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	public Utente getUtente(Long id) {
		
		Optional<Utente> result = this.utenteRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Utente> getAllUtenti() {
		
		List<Utente> utenti = new ArrayList<>();
		this.utenteRepository.findAll().forEach(utenti::add);
		
		return utenti;
	}
	
	@Transactional
	public Utente saveUtente(Utente utente) {
		
		return this.utenteRepository.save(utente);
	}
}
