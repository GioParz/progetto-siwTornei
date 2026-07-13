package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Utente;
import it.uniroma3.tornei.repository.UtenteRepository;

@Service
@Transactional(readOnly = true)
public class UtenteService {
	
	private final UtenteRepository utenteRepository;
	
	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
	}

	public Utente getUtente(Long id) {
		
		Optional<Utente> result = this.utenteRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Utente> getAllUtenti() {
		
		List<Utente> utenti = new ArrayList<>();
		this.utenteRepository.findAll().forEach(utenti::add);
		
		return utenti;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Utente saveUtente(Utente utente) {
		return this.utenteRepository.save(utente);
	}
}
