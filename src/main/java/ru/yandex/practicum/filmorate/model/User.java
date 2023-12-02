package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    @NotBlank (message = "Электронная почта не может быть пустой.")
    @Email (message = "Электронная почта не соответсвует формату.")
    private String email;
    @NotBlank (message = "Логин не может быть пустым и содержать пробелы.")
    private String login;
    private String name;
    @Past (message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}
