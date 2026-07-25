package it.uniroma3.tornei.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Torneo;

@Repository
public interface TorneoRepository extends CrudRepository<Torneo, Long> {
	
	boolean existsByNomeAndAnno(String nome, Integer anno);

	boolean existsByNomeAndAnnoAndIdNot(String nome, Integer anno, Long id);
	
	/**
	 * Dettaglio torneo con caricamento di tutte le partite e delle relative squadre (casa/ospite).
	 * Usato anche per la logica di calcolo della classifica.
	 */
	@Query("SELECT DISTINCT t FROM Torneo t " +
	       "LEFT JOIN FETCH t.partite p " +
	       "LEFT JOIN FETCH p.squadraCasa " +
	       "LEFT JOIN FETCH p.squadraOspite " +
	       "WHERE t.id = :id")
	Optional<Torneo> findByIdWithPartiteAndSquadre(@Param("id") Long id);

	/**
	 * Visualizzazione delle squadre partecipanti ad un determinato torneo.
	 */
	@Query("SELECT DISTINCT t FROM Torneo t LEFT JOIN FETCH t.squadre WHERE t.id = :id")
	Optional<Torneo> findByIdWithSquadre(@Param("id") Long id);
}
