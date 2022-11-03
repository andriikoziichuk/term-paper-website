package com.example.KursovaWebSite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private BigDecimal price;
}
