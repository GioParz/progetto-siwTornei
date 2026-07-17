package it.uniroma3.tornei.exception;

public class ArbitroDuplicatoException extends RuntimeException {
	
	public ArbitroDuplicatoException(String codiceAIA) {
		super("Un arbitro con codice AIA: " + codiceAIA + " esiste già.");
	}
}
