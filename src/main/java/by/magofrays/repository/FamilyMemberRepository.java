package by.magofrays.repository;

import by.magofrays.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {
    Optional<FamilyMember> findFamilyMemberByMember_Username(String username);
}
