package it.uniroma3.tornei.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.exception.SquadraDuplicataException;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.model.StatoPartita;
import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.repository.PartitaRepository;
import it.uniroma3.tornei.repository.SquadraRepository;

@Service
@Transactional(readOnly = true)
public class SquadraService {
	
	private final SquadraRepository squadraRepository;
	private final PartitaRepository partitaRepository;
	
	public SquadraService(SquadraRepository squadraRepository, PartitaRepository partitaRepository) {
		this.squadraRepository = squadraRepository;
		this.partitaRepository = partitaRepository;
	}

	public Squadra getSquadra(Long id) {
		return this.squadraRepository.findById(id).orElse(null);
	}
	
	public List<Squadra> getAllSquadre() {
		return (List<Squadra>) this.squadraRepository.findAll();
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Squadra saveSquadra(Squadra squadra) {
		
		boolean duplicato = squadra.getId() == null
				? this.squadraRepository.existsByNomeAndCitta(squadra.getNome(), squadra.getCitta())
				: this.squadraRepository.existsByNomeAndCittaAndIdNot(squadra.getNome(), squadra.getCitta(), squadra.getId());
		if(duplicato)
			throw new SquadraDuplicataException(squadra.getNome(), squadra.getCitta());
		
		return this.squadraRepository.save(squadra);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteSquadra(Long id) {
		
		Squadra squadra = this.squadraRepository.findById(id).orElse(null);
		
		if(squadra != null) {
			List<Partita> partiteCasa = this.partitaRepository.findBySquadraCasa(squadra);
			for(Partita p : partiteCasa) {
				if(p.getStato().equals(StatoPartita.PROGRAMMATA)) {
					this.partitaRepository.delete(p);
				} else {
					p.setSquadraCasaNomeStorico(squadra.getNome());
					p.setSquadraCasa(null);
					this.partitaRepository.save(p);
				}
			}
			
			List<Partita> partiteOspite = this.partitaRepository.findBySquadraOspite(squadra);
			for(Partita p : partiteOspite) {
				if(p.getStato().equals(StatoPartita.PROGRAMMATA)) {
					this.partitaRepository.delete(p);
				} else {
					p.setSquadraOspiteNomeStorico(squadra.getNome());
					p.setSquadraOspite(null);
					this.partitaRepository.save(p);
				}
			}
			
			if (squadra.getTornei() != null) {
	            for (Torneo torneo : squadra.getTornei()) {
	                torneo.getSquadre().remove(squadra);
	            }
	        }
	        
	        this.squadraRepository.delete(squadra);
		}
	}
}
