package by.magofrays.validation;

import by.magofrays.entity.Family;
import by.magofrays.entity.FamilyMember;
import by.magofrays.repository.FamilyMemberRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InFamilyValidator implements ConstraintValidator<InFamily, Object> {

    private final FamilyMemberRepository familyMemberRepository;
    private String usernameField;
    private String familyField;


    @Override
    public void initialize(InFamily constraintAnnotation) {
        this.usernameField = constraintAnnotation.familyField();
        this.familyField = constraintAnnotation.memberField();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        UUID familyId = getFieldValue(value, familyField);
        UUID memberId = getFieldValue(value, usernameField);
        if(memberId == null){
            return true;
        }
        return familyMemberRepository
                .findById(memberId)
                .map(FamilyMember::getFamily)
                .map(Family::getId)
                .map(id -> id.equals(familyId))
                .orElse(false);
    }

    @SneakyThrows
    private <T> T getFieldValue(Object object, String fieldName) {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object fieldValue = field.get(object);
        return ((Class<T>) UUID.class).cast(fieldValue);
    }
}
