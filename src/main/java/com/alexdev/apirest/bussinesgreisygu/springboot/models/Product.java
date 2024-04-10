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

    private String name;

    private String description;

    private Double price;

    @ManyToOne(targetEntity = Category.class)
    Category category;
}
