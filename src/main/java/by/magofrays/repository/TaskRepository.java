package by.magofrays.repository;

import by.magofrays.entity.Member;
import by.magofrays.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> getTasksByIssuedToId(UUID issuedToId);
}
