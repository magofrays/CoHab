package by.magofrays.repository;

import by.magofrays.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> getTasksByIssuedToId(UUID issuedToId);

    List<Task> getTasksByCreatedBy_Family_Id(UUID familyId);

    Optional<Task> getTaskByCreatedBy_Member_IdAndId(UUID memberId, UUID taskId);

    @Query("""
    SELECT COUNT(t) > 0 
    FROM Task t 
    WHERE t.id = :taskId AND t.createdBy.id = :memberId
    """)
    Boolean isCreatedBy(@Param("memberId") UUID memberId, @Param("taskId") UUID taskId);
}
