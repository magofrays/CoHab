package by.magofrays.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Constraint(validatedBy = BadLoginValidation.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BadLogin {
    String message() default "username or password are incorrect";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}


