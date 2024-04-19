package com.alexdev.apirest.bussinesgreisygu.springboot.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String img;

    private String description;

    private Double price;

    private Boolean available;

    @ManyToOne
    Category category;
}
