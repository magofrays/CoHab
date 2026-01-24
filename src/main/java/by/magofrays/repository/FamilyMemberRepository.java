package by.magofrays.repository;

import by.magofrays.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {
    Optional<FamilyMember> findByMember_usernameAndFamily_Id(String username, UUID familyId);
    Optional<FamilyMember> findByMember_IdAndFamily_Id(UUID memberId, UUID familyId);
    List<FamilyMember> findByFamily_Id(UUID familyId);
}
