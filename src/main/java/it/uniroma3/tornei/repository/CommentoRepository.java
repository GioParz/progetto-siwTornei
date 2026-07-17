package it.uniroma3.tornei.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Partita;

@Repository
public interface CommentoRepository extends CrudRepository<Commento, Long> {
	
	List<Commento> findByPartita(Partita partita);
}
