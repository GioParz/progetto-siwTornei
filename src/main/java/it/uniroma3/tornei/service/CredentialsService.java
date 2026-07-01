package it.uniroma3.tornei.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.model.RuoloUtente;
import it.uniroma3.tornei.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	public Credentials getCredentials(Long id) {
		
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public Credentials getcCredentials(String username) {
		
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		
		credentials.setRuolo(RuoloUtente.REGISTRATO);
		
		return this.credentialsRepository.save(credentials);
	}
}
