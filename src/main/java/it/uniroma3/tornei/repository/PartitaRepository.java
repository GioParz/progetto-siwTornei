package it.uniroma3.tornei.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Arbitro;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.Squadra;

@Repository
public interface PartitaRepository extends CrudRepository<Partita, Long> {
	
	List<Partita> findBySquadraCasa(Squadra squadraCasa);
	
	List<Partita> findBySquadraOspite(Squadra squadraOspite);
	
	List<Partita> findByArbitro(Arbitro arbitro);
	
	boolean existsByArbitroAndDataEOra(Arbitro arbitro, LocalDateTime dataEOra);
	
	@Query("SELECT COUNT(p) > 0 FROM Partita p WHERE " +
			"(p.squadraCasa = :squadra OR p.squadraOspite = :squadra)" +
			"AND p.dataEOra BETWEEN :inizioGiorno AND :fineGiorno")
	boolean isSquadraImpegnata(@Param("squadra") Squadra squadra, 
			@Param("inizioGiorno") LocalDateTime inizioGiorno, @Param("fineGiorno") LocalDateTime fineGiorno);
	
	/**
	 * Mantiene l'ottimizzazione tramite EntityGraph per il calendario del torneo.
	 * Recupera in un'unica JOIN le 4 relazioni @ManyToOne (torneo, squadraCasa, squadraOspite, arbitro).
	 */
	@EntityGraph(attributePaths = { "torneo", "squadraCasa", "squadraOspite", "arbitro" }, type = EntityGraphType.FETCH)
	List<Partita> findByTorneo_Id(Long torneoId);

	/**
	 * Dettaglio partita comprensivo di torneo, squadre, arbitro e lista commenti con autore.
	 */
	@Query("SELECT p FROM Partita p " +
			"LEFT JOIN FETCH p.torneo " +
			"LEFT JOIN FETCH p.squadraCasa " +
			"LEFT JOIN FETCH p.squadraOspite " +
			"LEFT JOIN FETCH p.arbitro " +
			"LEFT JOIN FETCH p.commenti c " +
			"LEFT JOIN FETCH c.utente " +
			"WHERE p.id = :id")
	Optional<Partita> findByIdWithCommenti(@Param("id") Long id);
}
