package it.uniroma3.tornei.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.Squadra;

@Repository
public interface PartitaRepository extends CrudRepository<Partita, Long> {
	
	public List<Partita> findBySquadraCasa(Squadra squadraCasa);
	
	public List<Partita> findBySquadraOspite(Squadra squadraOspite);
}
