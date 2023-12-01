package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Builder(toBuilder = true)
@Value
public class User {
    @NotNull
    Integer id;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String login;
    String name;
    @Past
    LocalDate birthday;
}
