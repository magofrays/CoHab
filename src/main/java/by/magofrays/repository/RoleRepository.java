package by.magofrays.repository;

import by.magofrays.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByNameAndFamily_Id(String roleName, UUID familyID);
}
