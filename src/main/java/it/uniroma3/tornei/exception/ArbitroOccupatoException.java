package it.uniroma3.tornei.exception;

public class ArbitroOccupatoException extends RuntimeException {
    
	public ArbitroOccupatoException(String nome, String cognome) {
        super("L'arbitro " + nome + " " + cognome + " è già designato per un'altra partita in questa data.");
    }
}
