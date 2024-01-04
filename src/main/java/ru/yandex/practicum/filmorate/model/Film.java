package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Film {
    private Integer id;
    @NotBlank (message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    private LocalDate releaseDate;
    private List<Genre> genre;
    private MPARating mparating;
    @Positive(message = "Продолжительность фильма должна быть положительной.")
    private int duration;
    private List<Integer> likes = new ArrayList<>();
}
