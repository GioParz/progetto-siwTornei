package it.uniroma3.tornei.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Torneo;

@Repository
public interface TorneoRepository extends CrudRepository<Torneo, Long> {
	
	@Query("SELECT DISTINCT t FROM Torneo t " +
	           "LEFT JOIN FETCH t.partite p " +
	           "LEFT JOIN FETCH p.squadraCasa " +
	           "LEFT JOIN FETCH p.squadraOspite " +
	           "WHERE t.id = :id")
	Optional<Torneo> findByIdWithPartiteAndSquadre(@Param("id") Long id);
	
	boolean existsByNomeAndAnno(String nome, Integer anno);

	boolean existsByNomeAndAnnoAndIdNot(String nome, Integer anno, Long id);
}
