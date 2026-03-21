package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String taskName;
    private String description;
    @ManyToOne
    private FamilyMember createdBy;
    @ManyToOne
    private FamilyMember issuedTo;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;

    private Boolean isMarked;
    private Boolean isChecked;
}
