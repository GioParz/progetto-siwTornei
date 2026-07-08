package it.uniroma3.tornei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.repository.CommentoRepository;

@Service
public class CommentoService {
	
	@Autowired
	private CommentoRepository commentoRepository;
	
	@Transactional(readOnly = true)
	public Commento getCommento(Long id) {
		
		return this.commentoRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Commento saveCommento(Commento commento) {
		
		return this.commentoRepository.save(commento);
	}
	
	@Transactional
	public void deleteCommento(Long id) {
		
		this.commentoRepository.deleteById(id);
	}
}
