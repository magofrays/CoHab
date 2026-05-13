package by.magofrays.repository;

import by.magofrays.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> getTasksByIssuedToId(UUID issuedToId);

    List<Task> getTasksByCreatedBy_Family_Id(UUID familyId);

    Optional<Task> getTaskByCreatedBy_Member_IdAndId(UUID memberId, UUID taskId);

    @Query("""
                SELECT EXISTS(
                    select 1 from Task t where t.id = :taskId and t.createdBy.id = :memberId)
            """)
    Boolean isCreatedBy(UUID memberId, UUID taskId);
}
