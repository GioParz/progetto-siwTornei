package it.uniroma3.tornei.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.model.Arbitro;
import it.uniroma3.tornei.repository.ArbitroRepository;

@Service
@Transactional(readOnly = true)
public class ArbitroService {
	
	private final ArbitroRepository arbitroRepository;
	
	public ArbitroService(ArbitroRepository arbitroRepository) {
		this.arbitroRepository = arbitroRepository;
	}

	public Arbitro getArbitroById(Long id) {
		
		Optional<Arbitro> result = this.arbitroRepository.findById(id);
		
		return result.orElse(null);
	}
	
	public Arbitro getArbitroByCodiceAIA(String codiceAIA) {
		return this.arbitroRepository.findByCodiceAIA(codiceAIA);
	}
	
	public List<Arbitro> getAllArbitri() {
		
		List<Arbitro> arbitri = new ArrayList<>();
		this.arbitroRepository.findAll().forEach(arbitri::add);
		
		return arbitri;
	}
	
	public boolean existsByCodiceAIA(String codiceAIA) {
		return this.arbitroRepository.existsByCodiceAIA(codiceAIA);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Arbitro saveArbitro(Arbitro arbitro) {
		return this.arbitroRepository.save(arbitro);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deleteArbitroById(Long id) {
		this.arbitroRepository.deleteById(id);
	}
}
