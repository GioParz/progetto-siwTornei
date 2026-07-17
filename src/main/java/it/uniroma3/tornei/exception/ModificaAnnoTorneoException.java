package it.uniroma3.tornei.exception;

public class ModificaAnnoTorneoException extends RuntimeException {
    
	public ModificaAnnoTorneoException() {
        super("Non puoi alterare la coerenza temporale di un torneo già iniziato.");
    }
}
