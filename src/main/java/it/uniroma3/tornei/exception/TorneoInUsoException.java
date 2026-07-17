package it.uniroma3.tornei.exception;

public class TorneoInUsoException extends RuntimeException {
    
	public TorneoInUsoException() {
        super("Impossibile eliminare un torneo in cui sono già state disputate delle partite.");
    }
}
