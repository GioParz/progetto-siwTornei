package it.uniroma3.tornei.exception;

public class IncompatibilitaDataPartitaException extends RuntimeException {
    
	public IncompatibilitaDataPartitaException() {
        super("La data della partita non è compatibile: verifica che non sfori l'anno del torneo o che le squadre non abbiano già altri impegni quel giorno.");
    }
}
