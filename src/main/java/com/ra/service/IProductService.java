package com.ra.service;

import com.ra.dto.req.ProductRequest;
import com.ra.exception.CustomException;
import com.ra.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> findAll(Pageable pageable, String search);

    Product findById(Long productId) throws CustomException;

    Product save(ProductRequest productRequest) throws CustomException;

    Product update(ProductRequest productRequest, Long updateId) throws CustomException;

    void deleteById(Long deleteId) throws CustomException;
}
