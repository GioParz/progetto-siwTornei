package it.uniroma3.tornei.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	private final CredentialsRepository credentialsRepository;
	private final PasswordEncoder passwordEncoder;
	
	public CredentialsService(CredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder) {
		this.credentialsRepository = credentialsRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Credentials getCredentials(Long id) {
		
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public Credentials getCredentials(String username) {
		
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		
		String passwordInChiaro = credentials.getPassword();
		String passwordCifrata = this.passwordEncoder.encode(passwordInChiaro);
		
		credentials.setPassword(passwordCifrata);
		
		return this.credentialsRepository.save(credentials);
	}
}
