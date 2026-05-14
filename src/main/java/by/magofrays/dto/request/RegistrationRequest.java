package by.magofrays.dto.request;

import by.magofrays.entity.Member;
import by.magofrays.validation.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegistrationRequest(
        @Unique(message = "Пользователь с таким никнеймом уже существует!", entityClass = Member.class, fieldName = "username")
        @NotBlank(message = "Никнейм не может быть пустым!")
        String username,
        @NotBlank(message = "Введите пароль!")
        String password,
        @NotBlank(message = "Заполните имя!")
        String firstname,
        @NotBlank(message = "Заполните фамилию!")
        String lastname,
        @NotNull(message = "Дата рождения не должна быть пустой!")
        LocalDate birthDate
) {

}
