package it.uniroma3.tornei.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.repository.PartitaRepository;

@Service
@Transactional(readOnly = true)
public class PartitaService {
	
	private final PartitaRepository partitaRepository;
	
	public PartitaService(PartitaRepository partitaRepository) {
		this.partitaRepository = partitaRepository;
	}

	public Partita getPartita(Long id) {
		
		Optional<Partita> result = this.partitaRepository.findById(id);
		
		return result.orElse(null);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Partita savePartita(Partita partita) {
		return this.partitaRepository.save(partita);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deletePartita(Long id) {
		this.partitaRepository.deleteById(id);
	}
}
