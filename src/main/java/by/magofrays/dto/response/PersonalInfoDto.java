package by.magofrays.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonalInfoDto(
        @NotBlank(message = "Заполните имя!")
        String firstname,
        @NotBlank(message = "Заполните фамилию!")
        String lastname,
        @NotNull(message = "Дата рождения не должна быть пустой!")
        LocalDate birthDate
) {
}
