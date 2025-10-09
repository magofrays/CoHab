package by.magofrays.validation;

import by.magofrays.dto.SmallMemberDto;
import by.magofrays.repository.MemberRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BadLoginValidation implements ConstraintValidator<BadLogin, SmallMemberDto> {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean isValid(SmallMemberDto createMemberDto, ConstraintValidatorContext constraintValidatorContext) {
        var member = memberRepository.findByUsername(createMemberDto.getUsername());
        return member.filter(value ->
                passwordEncoder
                        .matches(createMemberDto.getPassword(), value.getPassword()))
                .isPresent();
    }
}
