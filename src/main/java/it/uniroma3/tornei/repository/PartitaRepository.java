package it.uniroma3.tornei.repository;

import java.time.LocalDateTime;
import java.util.List;

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
}
