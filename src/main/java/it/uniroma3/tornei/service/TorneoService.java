package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.repository.TorneoRepository;

@Service
public class TorneoService {
	
	@Autowired
	private TorneoRepository torneoRepository;
	
	public Torneo getTorneo(Long id) {
		
		Optional<Torneo> result = this.torneoRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public List<Torneo> getAllTornei() {
		
		List<Torneo> tornei = new ArrayList<>();
		//aggiungiamo ogni elemento restituito da findAll dentro tornei
		this.torneoRepository.findAll().forEach(tornei::add);
		
		return tornei;
	}
	
	@Transactional
	public Torneo saveTorneo(Torneo torneo) {
		
		return this.torneoRepository.save(torneo);
	}
	
	@Transactional
	public void deleteTorneo(Long id) {
		//la cancellazione per id è più efficiente in quanto non carica tutto l'oggetto in memoria prima di cancellarlo
		this.torneoRepository.deleteById(id);
	}
}
