package com.example.KursovaWebSite.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private BigDecimal price;
    private String photoPath;
    private String authorFullName;
}
