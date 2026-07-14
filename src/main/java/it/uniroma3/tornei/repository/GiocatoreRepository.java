package it.uniroma3.tornei.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.model.RuoloGiocatore;

@Repository
public interface GiocatoreRepository extends CrudRepository<Giocatore, Long> {

	public boolean existsByCognomeAndDataNascitaAndRuolo(String cognome, LocalDate dataNascita, RuoloGiocatore ruolo);
}
