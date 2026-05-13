package by.magofrays.repository;

import by.magofrays.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FamilyRepository extends JpaRepository<Family, UUID> {
    Optional<Family> findFamilyByFamilyName(String familyName);
}
