package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


// by extending by JpaRepository we can easily perform crud on repo
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
