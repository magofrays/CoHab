package by.magofrays.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UniqueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "User with this nickname already exists";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    Class<?> entityClass();

    String fieldName();
}
