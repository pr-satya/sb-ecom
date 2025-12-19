package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImp implements CategoryService {
   // private List<Category> categories = new ArrayList<>();
   // private Long nextId=1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageDetails= PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Category> categoryPage= categoryRepository.findAll(pageDetails);

        List<Category> categories=categoryPage.getContent();
        if(categories.isEmpty()){
            throw new APIException("No Category has created yet.");
        }
        List<CategoryDTO> categoryDTOS=categories.stream()
                .map(category ->modelMapper.map(category, CategoryDTO.class)).toList();

        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElement(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB=categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDB != null){
            throw new APIException("Category with Category name "+category.getCategoryName()+" is already exist!!");
        }
        //category.setCategoryId(nextId++);
        Category savedcategory=categoryRepository.save(category);
        return modelMapper.map(savedcategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        //Category category=modelMapper.map(categoryDTO, Category.class);
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        //Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryId);
        Category category=modelMapper.map(categoryDTO, Category.class);
        Category savedCategory=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);


        return modelMapper.map(savedCategory, CategoryDTO.class);

    }
}
