package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.repository.SquadraRepository;

@Service
public class SquadraService {
	
	@Autowired
	private SquadraRepository squadraRepository;
	
	public Squadra getSquadra(long id) {
		
		Optional<Squadra> result = this.squadraRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Squadra> getAllSquadre() {
		
		List<Squadra> squadre = new ArrayList<>();
		this.squadraRepository.findAll().forEach(squadre::add);
		
		return squadre;
	}
	
	@Transactional
	public Squadra saveSquadra(Squadra squadra) {
		
		return this.squadraRepository.save(squadra);
	}
}
