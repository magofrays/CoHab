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
    private UUID id;
    private String taskName;
    private String description;
    @ManyToOne
    private FamilyMember createdBy;
    @ManyToOne
    private FamilyMember issuedTo;
    private LocalDate createdDate;
    private LocalDate dueDate;

    private Boolean isMarked;
    private Boolean isChecked;
}
