package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.apache.logging.log4j.message.Message;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private Integer id;
    @NotBlank (message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    private LocalDate releaseDate;
    @PositiveOrZero (message = "Продолжительность фильма должна быть положительной.")
    private int duration;
}
