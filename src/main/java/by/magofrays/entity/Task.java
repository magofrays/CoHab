package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Task {
    @Id
    @org.hibernate.validator.constraints.UUID
    private UUID uuid;
    private String taskName;
}
