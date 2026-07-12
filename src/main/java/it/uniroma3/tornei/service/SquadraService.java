package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.model.StatoPartita;
import it.uniroma3.tornei.repository.PartitaRepository;
import it.uniroma3.tornei.repository.SquadraRepository;

@Service
public class SquadraService {
	
	@Autowired
	private SquadraRepository squadraRepository;
	@Autowired
	private PartitaRepository partitaRepository;
	
	public Squadra getSquadra(Long id) {
		
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
	
	@Transactional
	public void deleteSquadra(Long id) {
		
		Squadra squadra = this.squadraRepository.findById(id).orElse(null);
		
		if(squadra != null) {
			List<Partita> partiteCasa = this.partitaRepository.findBySquadraCasa(squadra);
			for(Partita p : partiteCasa) {
				if(p.getStato().equals(StatoPartita.PROGRAMMATA)) {
					this.partitaRepository.delete(p);
				} else {
					p.setSquadraCasa(null);
					this.partitaRepository.save(p);
				}
			}
			
			List<Partita> partiteOspite = this.partitaRepository.findBySquadraOspite(squadra);
			for(Partita p : partiteOspite) {
				if(p.getStato().equals(StatoPartita.PROGRAMMATA)) {
					this.partitaRepository.delete(p);
				} else {
					p.setSquadraOspite(null);
					this.partitaRepository.save(p);
				}
			}
		}
		
		this.squadraRepository.deleteById(id);
	}
}
