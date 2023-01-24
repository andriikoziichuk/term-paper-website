package com.example.KursovaWebSite.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BucketDTO {
    private int amountProducts;
    private Double sum;
    private List<BucketDetailDTO> bucketDetails = new ArrayList<>();

    public void aggregate() {
        this.amountProducts = bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
