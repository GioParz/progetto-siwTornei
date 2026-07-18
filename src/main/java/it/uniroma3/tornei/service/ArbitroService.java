package it.uniroma3.tornei.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.exception.ArbitroDuplicatoException;
import it.uniroma3.tornei.model.Arbitro;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.repository.ArbitroRepository;
import it.uniroma3.tornei.repository.PartitaRepository;

@Service
@Transactional(readOnly = true)
public class ArbitroService {
	
	private final ArbitroRepository arbitroRepository;
	private final PartitaRepository partitaRepository;
	
	public ArbitroService(ArbitroRepository arbitroRepository, PartitaRepository partitaRepository) {
		this.arbitroRepository = arbitroRepository;
		this.partitaRepository = partitaRepository;
	}

	public Arbitro getArbitroById(Long id) {
		return this.arbitroRepository.findById(id).orElse(null);
	}
	
	public Arbitro getArbitroByCodiceAIA(String codiceAIA) {
		return this.arbitroRepository.findByCodiceAIA(codiceAIA).orElse(null);
	}
	
	public List<Arbitro> getAllArbitri() {
		return (List<Arbitro>) this.arbitroRepository.findAll();
	}
	
	public boolean existsByCodiceAIA(String codiceAIA) {
		return this.arbitroRepository.existsByCodiceAIA(codiceAIA);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Arbitro saveArbitro(Arbitro arbitro) throws ArbitroDuplicatoException {
		
		boolean duplicato = this.arbitroRepository.existsByCodiceAIA(arbitro.getCodiceAIA());
		if(duplicato)
			throw new ArbitroDuplicatoException(arbitro.getCodiceAIA());
		
		return this.arbitroRepository.save(arbitro);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteArbitroById(Long id) {
		
		Arbitro arbitro = this.arbitroRepository.findById(id).orElse(null);
		
		if(arbitro != null) {
			List<Partita> partite = this.partitaRepository.findByArbitro(arbitro);
			
			for(Partita p : partite) {
				p.setArbitroNomeCognomeStorico(arbitro.getNome() + " " + arbitro.getCognome());
				p.setArbitro(null);
				this.partitaRepository.save(p);
			}
			
			this.arbitroRepository.deleteById(id);
		}
	}
}
