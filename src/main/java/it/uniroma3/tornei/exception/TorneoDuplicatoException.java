package it.uniroma3.tornei.exception;

public class TorneoDuplicatoException extends RuntimeException {
    
	public TorneoDuplicatoException(String nome, Integer anno) {
        super("Il torneo " + nome + " " + anno + " è già presente nel sistema.");
    }
}