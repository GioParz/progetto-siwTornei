package it.uniroma3.tornei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Squadra;

@Repository
public interface SquadraRepository extends CrudRepository<Squadra, Long> {

}
