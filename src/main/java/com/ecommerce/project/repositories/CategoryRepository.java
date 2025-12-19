package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;


// by extending by JpaRepository we can easily perform crud on repo
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryName(
             @NotBlank
             @Size(min = 5,message="CategoryName must contain at-least 5 character")
             String categoryName);
}
