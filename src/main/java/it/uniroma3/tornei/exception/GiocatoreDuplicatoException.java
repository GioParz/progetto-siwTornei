package it.uniroma3.tornei.exception;

public class GiocatoreDuplicatoException extends RuntimeException {
    
	public GiocatoreDuplicatoException(String nome, String cognome) {
        super(nome + " " + cognome + " è già presente nel sistema.");
    }
}