package by.magofrays.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = InFamilyValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InFamily {
    String message() default "No such member in family";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String memberField();
    String familyField();
}
