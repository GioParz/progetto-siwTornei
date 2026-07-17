package it.uniroma3.tornei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {
	
	boolean existsByEmail(String email);
}
