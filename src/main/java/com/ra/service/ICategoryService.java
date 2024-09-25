package com.ra.service;

import com.ra.dto.req.CategoryRequest;
import com.ra.exception.CustomException;
import com.ra.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService
{
	Page<Category> findAll(Pageable pageable, String search);
	
	Category findById(Long categoryId) throws CustomException;
	
	Category save(CategoryRequest categoryRequest);
	
	Category update(CategoryRequest categoryRequest, Long updateId) throws CustomException;

	void deleteById(Long deleteId) throws CustomException;
}
