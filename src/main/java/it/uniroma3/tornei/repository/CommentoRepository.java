package it.uniroma3.tornei.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Partita;

public interface CommentoRepository extends CrudRepository<Commento, Long> {
	
	public List<Commento> findByPartita(Partita partita);
}
