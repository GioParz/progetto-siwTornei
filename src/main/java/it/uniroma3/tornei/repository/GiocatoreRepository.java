package it.uniroma3.tornei.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.model.RuoloGiocatore;

@Repository
public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

	boolean existsByNomeAndCognomeAndDataNascitaAndRuolo(String nome, String cognome, LocalDate dataNascita, RuoloGiocatore ruolo);
	
	boolean existsByNomeAndCognomeAndDataNascitaAndRuoloAndIdNot(String nome, String cognome, LocalDate dataNascita, RuoloGiocatore ruolo, Long id);

}
