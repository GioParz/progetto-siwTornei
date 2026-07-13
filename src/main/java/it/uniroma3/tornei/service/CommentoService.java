package it.uniroma3.tornei.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.repository.CommentoRepository;

@Service
@Transactional(readOnly = true)
public class CommentoService {
	
	private final CommentoRepository commentoRepository;
	
	public CommentoService(CommentoRepository commentoRepository) {
		this.commentoRepository = commentoRepository;
	}

	public Commento getCommento(Long id) {
		
		return this.commentoRepository.findById(id).orElse(null);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Commento saveCommento(Commento commento) {
		return this.commentoRepository.save(commento);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteCommento(Long id) {
		this.commentoRepository.deleteById(id);
	}
}
