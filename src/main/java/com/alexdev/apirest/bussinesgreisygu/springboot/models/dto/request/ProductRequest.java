package com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductRequest {
    private String description;
    private Double price;
    private String img;
    private Boolean available;
    Long category;
}
