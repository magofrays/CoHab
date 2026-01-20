package by.magofrays.service;

import by.magofrays.entity.Access;
import by.magofrays.exception.BusinessException;
import by.magofrays.exception.ErrorCode;
import by.magofrays.repository.FamilyMemberRepository;
import by.magofrays.repository.MemberRepository;
import by.magofrays.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccessService {
    private final FamilyMemberRepository memberRepository;

    @Transactional
    public List<Access> getAccessesByFamilyAndMember(UUID familyId, String username){
        var familyMember = memberRepository.findByMember_usernameAndFamily_Id(username, familyId).orElseThrow(
                () -> new BusinessException(ErrorCode.BAD_REQUEST, "Не удалось найти пользователя " + username + " в семье " + familyId + "!")
        );
        return familyMember.getRoles().stream().flatMap(role -> role.getAccessList().stream()).distinct().toList();
    }

}
