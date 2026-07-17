package it.uniroma3.tornei.validation;

import it.uniroma3.tornei.model.Partita;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotStessaSquadraValidator implements ConstraintValidator<NotStessaSquadra, Partita> {

	@Override
    public boolean isValid(Partita partita, ConstraintValidatorContext context) {
        
		if (partita == null)
            return true;

        if (partita.getSquadraCasa() == null || partita.getSquadraOspite() == null)
            return true;

        boolean valida = !partita.getSquadraCasa().equals(partita.getSquadraOspite());
        
        if (!valida) {
            // Disabilita l'errore globale predefinito
            context.disableDefaultConstraintViolation();
            // Associa l'errore specificamente al campo 'squadraOspite'
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("squadraOspite")
                   .addConstraintViolation();
        }
        
        return valida;
    }
}
