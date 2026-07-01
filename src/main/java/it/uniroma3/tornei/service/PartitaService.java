package it.uniroma3.tornei.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.repository.PartitaRepository;

@Service
public class PartitaService {
	
	@Autowired
	private PartitaRepository partitaRepository;
	
	public Partita getPartita(Long id) {
		
		Optional<Partita> result = this.partitaRepository.findById(id);
		
		return result.orElse(null);
	}
	
	@Transactional
	public Partita savePartita(Partita partita) {
		
		return this.partitaRepository.save(partita);
	}
}
