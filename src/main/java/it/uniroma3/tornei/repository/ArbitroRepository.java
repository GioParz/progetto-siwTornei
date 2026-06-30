package it.uniroma3.tornei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Arbitro;

@Repository
public interface ArbitroRepository extends CrudRepository<Arbitro, Long> {
	
	public Arbitro findByCodiceAIA(String codiceAIA);
}
