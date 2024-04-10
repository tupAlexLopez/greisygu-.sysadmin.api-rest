package com.alexdev.apirest.bussinesgreisygu.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Category {
    @Id
    private Long id;

    private String name;
}
