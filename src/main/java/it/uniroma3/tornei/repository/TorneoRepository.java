package it.uniroma3.tornei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Torneo;

@Repository
public interface TorneoRepository extends CrudRepository<Torneo, Long> {
	
	public boolean existsByNomeAndAnno(String nome, Integer anno);
}
