package by.magofrays.dto;

import by.magofrays.entity.Member;
import by.magofrays.validation.LoginGroup;
import by.magofrays.validation.RegistrationGroup;
import by.magofrays.validation.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegistrationDto {

    @Unique(message = "Пользователь с таким никнеймом уже существует!",
            entityClass = Member.class, fieldName = "username")
    @NotBlank(message = "Никнейм не может быть пустым!")
    private String username;
    @NotBlank(message = "Введите пароль!")
    private String password;
    @NotBlank(message = "Заполните имя!")
    private String firstname;
    @NotBlank(message = "Заполните фамилию!")
    private String lastname;
    @NotNull(message = "Дата рождения не должна быть пустой!") // из-за этой хуйни ломается все))
    private LocalDate birthDate;
}
