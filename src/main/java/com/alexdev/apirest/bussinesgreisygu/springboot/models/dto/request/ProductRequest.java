package com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
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
    Category category;
}
