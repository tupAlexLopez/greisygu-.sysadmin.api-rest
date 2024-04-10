package com.alexdev.apirest.bussinesgreisygu.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    private Long id;

    private String urlImg;

    private String description;

    private Double price;

    private Boolean available;

    private Integer stock;

    @ManyToOne(targetEntity = Category.class)
    Category category;
}
