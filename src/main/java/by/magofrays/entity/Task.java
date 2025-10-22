package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Task {
    @Id
    @org.hibernate.validator.constraints.UUID
    private UUID uuid;
    private String taskName;
    @ManyToOne
    private Member createdBy;
}
