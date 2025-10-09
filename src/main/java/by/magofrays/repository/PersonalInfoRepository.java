package by.magofrays.repository;

import by.magofrays.entity.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, UUID> {
    
}
