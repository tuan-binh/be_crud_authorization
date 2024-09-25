package com.ra.repository;

import com.ra.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long>
{
    Page<Product> findAllByNameContains(String name, Pageable pageable);

    boolean existsByCategoryId(Long categoryId);

    boolean existsByName(String name);
}
