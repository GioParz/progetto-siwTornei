package it.uniroma3.tornei.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.model.RuoloUtente;
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
	public void deleteCommento(Long id, Credentials credentialsLoggato) {
		
		Commento commento = this.commentoRepository.findById(id).orElse(null);
		if(commento == null)
			return;
		
		boolean isAutore = commento.getUtente().getId().equals(credentialsLoggato.getUtente().getId());
		boolean isAdmin = credentialsLoggato.getRuolo().equals(RuoloUtente.ADMIN);
		
		if(!isAutore && !isAdmin)
			throw new AccessDeniedException("Non hai l'autorizzazione per eliminare questo commento");
		
		this.commentoRepository.deleteById(id);
	}
}
