package it.uniroma3.tornei.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NotStessaSquadraValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotStessaSquadra {
    
    String message() default "Una squadra non può giocare contro se stessa.";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
