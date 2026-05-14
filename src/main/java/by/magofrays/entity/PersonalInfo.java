package by.magofrays.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String firstname;
    String lastname;
    LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "member_id", unique = true)
    Member member;
}
