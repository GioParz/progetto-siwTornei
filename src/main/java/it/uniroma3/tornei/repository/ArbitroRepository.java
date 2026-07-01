package it.uniroma3.tornei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Arbitro;

@Repository
public interface ArbitroRepository extends CrudRepository<Arbitro, Long> {
	
	/* per questo metodo ho scelto di non attuare una sicurezza troppo rigida
	 * perciò va bene che nel caso l'arbitro non esista riceva un semplice null
	 * e non un oggetto "scatola" Optional<> da dover poi gestire con i suoi metodi specifici
	 */
	public Arbitro findByCodiceAIA(String codiceAIA);
}
