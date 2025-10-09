package by.magofrays.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
public class PersonalInfoDto {
    @NotBlank(message = "Заполните имя!")
    String firstname;
    @NotBlank(message = "Заполните фамилию!")
    String lastname;
    @NotNull(message = "Дата рождения не должна быть пустой!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate;
}
