package com.ra.repository;

import com.ra.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
    Page<Category> findAllByNameContains(String name, Pageable pageable);

    boolean existsByName(String name);
}
