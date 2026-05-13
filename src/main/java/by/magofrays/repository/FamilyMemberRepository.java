package by.magofrays.repository;

import by.magofrays.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, UUID> {
    Optional<FamilyMember> findByMember_usernameAndFamily_Id(String username, UUID familyId);

    @Query("""
                select exists(
                    select 1 from FamilyMember fm 
                        where fm.family.id = :familyId and fm.member.id = :memberId 
                    )
            """)
    Boolean memberInFamily(@Param("memberId") UUID memberId, @Param("familyId") UUID familyId);

    Optional<FamilyMember> findByMember_IdAndFamily_Id(UUID memberId, UUID familyId);

    List<FamilyMember> findByFamily_Id(UUID familyId);
}
