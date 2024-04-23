package com.alexdev.apirest.bussinesgreisygu.springboot.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Schema(name = "Producto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Descripcion del producto.")
    private String description;

    @Schema(example = "1250.00")
    private Double price;

    @Schema(example = "true")
    private Boolean available;

    @Schema(example = "ravioles.png")
    private String img;

    @ManyToOne
    Category category;
}
