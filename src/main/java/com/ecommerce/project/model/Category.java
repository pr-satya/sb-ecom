package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//The @Entity annotation is used in JPA (Java Persistence API) to mark a class as a database table.
@Entity(name="CATEGORIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    //@id means we are making categoryId as a primary key and specifying the generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min = 5,message="CategoryName must contain at-least 5 character")
    private String categoryName;

}
