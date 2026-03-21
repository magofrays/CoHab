package by.magofrays.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {
    @Id
    UUID id;
    String firstname;
    String lastname;
    LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "member_id", unique = true)
    Member member;
}
