package com.ra.service.impl;

import com.ra.dto.req.CategoryRequest;
import com.ra.exception.CustomException;
import com.ra.model.Category;
import com.ra.repository.ICategoryRepository;
import com.ra.repository.IProductRepository;
import com.ra.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService
{
    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;

    @Override
    public Page<Category> findAll(Pageable pageable, String search)
    {
        Page<Category> categories;
        if (search.isEmpty())
        {
            categories = categoryRepository.findAll(pageable);
        }
        else
        {
            categories = categoryRepository.findAllByNameContains(search, pageable);
        }
        return categories;
    }

    @Override
    public Category findById(Long categoryId) throws CustomException
    {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CustomException("không tìm thấy danh mục", HttpStatus.NOT_FOUND));
    }

    @Override
    public Category save(CategoryRequest categoryRequest)
    {
        return categoryRepository.save(requestToEntity(categoryRequest));
    }

    @Override
    public Category update(CategoryRequest categoryRequest, Long updateId) throws CustomException
    {
        Category category = findById(updateId);
        category.setName(categoryRequest.getCategoryName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long deleteId) throws CustomException
    {
        if (categoryRepository.existsById(deleteId))
        {
            if (productRepository.existsByCategoryId(deleteId))
            {
                Category category = findById(deleteId);
                category.setStatus(!category.getStatus());
                categoryRepository.save(category);
            }
            else
            {
                categoryRepository.deleteById(deleteId);
            }
        }
        else
        {
            throw new CustomException("không tìm thấy danh mục", HttpStatus.NOT_FOUND);
        }
    }

    public Category requestToEntity(CategoryRequest categoryRequest)
    {
        return Category.builder()
                .name(categoryRequest.getCategoryName())
                .build();
    }
}
