package it.uniroma3.tornei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Giocatore;

@Repository
public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

}
