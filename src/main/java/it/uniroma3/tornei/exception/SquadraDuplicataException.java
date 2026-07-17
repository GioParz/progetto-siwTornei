package it.uniroma3.tornei.exception;

public class SquadraDuplicataException extends RuntimeException {
	
	public SquadraDuplicataException(String nome, String citta) {
		super("La squadra " + nome + " di " + citta + " è già presente nel sistema.");
	}
}
