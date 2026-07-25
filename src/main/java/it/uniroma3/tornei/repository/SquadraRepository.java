package it.uniroma3.tornei.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Squadra;

@Repository
public interface SquadraRepository extends CrudRepository<Squadra, Long> {
	
	boolean existsByNomeAndCitta(String nome, String citta);
	
	boolean existsByNomeAndCittaAndIdNot(String nome, String citta, Long id);
	
	/**
	 * Recupera la singola squadra caricando contestualmente la lista dei suoi giocatori.
	 */
	@EntityGraph(attributePaths = "giocatori", type = EntityGraphType.FETCH)
	@Query("SELECT s FROM Squadra s WHERE s.id = :id")
	Optional<Squadra> findByIdWithGiocatori(@Param("id") Long id);
	
	/**
	 * Recupera l'elenco completo delle squadre caricando per ciascuna la relativa rosa di giocatori.
	 */
	@EntityGraph(attributePaths = "giocatori", type = EntityGraphType.FETCH)
	@Query("SELECT s FROM Squadra s")
	List<Squadra> findAllWithGiocatori();
}
