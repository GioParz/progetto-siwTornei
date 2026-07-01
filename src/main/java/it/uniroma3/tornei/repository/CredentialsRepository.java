package it.uniroma3.tornei.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.tornei.model.Credentials;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	/* questo metodo verrà dato in pasto a Spring Security per il login
	 * perciò ha bisogno di Optional<> per gestire correttamente gli errori di login
	 */
	public Optional<Credentials> findByUsername(String username);
}
