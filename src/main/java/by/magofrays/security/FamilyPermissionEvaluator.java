package by.magofrays.security;

import by.magofrays.entity.Access;
import by.magofrays.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FamilyPermissionEvaluator implements PermissionEvaluator {
    private final FamilyRepository familyRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false; //todo
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if(authentication == null || !authentication.isAuthenticated()){
            return false;
        }
        if(Objects.equals(targetType, "family")) {
            var family = familyRepository.findFamilyByFamilyName((String) targetId); //todo лучше из бд получать access
            if (family.isEmpty()) {
                return false;
            }
            var familyEntity = family.get();
            var principal = (MemberPrincipal) authentication.getPrincipal();
            var accesses = principal.getFamilyAccesses().get(familyEntity.getId());
            if (accesses == null) {
                return false;
            }
            return accesses.stream().anyMatch(access -> access.name().equals(permission.toString()));
        }
        return false;
    }
}
