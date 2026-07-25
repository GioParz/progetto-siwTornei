package it.uniroma3.tornei.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Giocatore;

@Repository
public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

	boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);
	
	boolean existsByNomeAndCognomeAndDataNascitaAndIdNot(String nome, String cognome, LocalDate dataNascita, Long id);

}
