package com.example.KursovaWebSite.dtos;

import com.example.KursovaWebSite.models.book.Author;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    @NotEmpty(message = "Please, fill the book title (Title cannot be empty)")
    @Size(min = 5, max = 256, message = "Title of the book should be between 5 and 256 characters")
    private String title;
    @NotEmpty(message = "Please, fill the book description (Description cannot be empty)")
    @Size(max = 2048, message = "Description of the book should be between 0 and 2048 characters")
    private String description;
    @Min(value = 0, message = "Price cannot be lower than 0")
    private double price;
    private String photoPath;
    private Author authorFullName;
}
