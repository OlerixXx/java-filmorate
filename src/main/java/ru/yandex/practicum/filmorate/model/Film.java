package ru.yandex.practicum.filmorate.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Value
public class Film {
    @NotNull
    Integer id;
    @NotBlank
    String name;
    @Size(max = 200)
    String description;
    LocalDate releaseDate;
    Duration duration;
}
