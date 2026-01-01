package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Task {
    @Id
    @org.hibernate.validator.constraints.UUID
    private UUID id;
    private String taskName;
    private String description;
    @ManyToOne
    private Member createdBy;
    @ManyToOne
    private Member issuedTo;
    private LocalDate createdDate;
    private LocalDate dueDate;
}
