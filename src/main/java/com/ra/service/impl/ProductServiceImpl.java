package com.ra.service.impl;

import com.ra.dto.req.ProductRequest;
import com.ra.exception.CustomException;
import com.ra.model.Product;
import com.ra.repository.IProductRepository;
import com.ra.service.ICategoryService;
import com.ra.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService
{

    private final IProductRepository productRepository;
    private final ICategoryService categoryService;

    @Override
    public Page<Product> findAll(Pageable pageable, String search)
    {
        Page<Product> products;
        if (search.isEmpty())
        {
            products = productRepository.findAll(pageable);
        }
        else
        {
            products = productRepository.findAllByNameContains(search, pageable);
        }
        return products;
    }

    @Override
    public Product findById(Long productId) throws CustomException
    {
        return productRepository.findById(productId).orElseThrow(() -> new CustomException("không tìm thấy sản phẩm", HttpStatus.NOT_FOUND));
    }

    @Override
    public Product save(ProductRequest productRequest) throws CustomException
    {
        return productRepository.save(toEntity(productRequest));
    }

    @Override
    public Product update(ProductRequest productRequest, Long updateId) throws CustomException
    {
        Product product = findById(updateId);
        product.setName(productRequest.getName());
        product.setImage(productRequest.getImage());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setCategory(categoryService.findById(productRequest.getCategoryId()));
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long deleteId) throws CustomException
    {
        if (productRepository.existsById(deleteId))
        {
            productRepository.deleteById(deleteId);
        }
        else
        {
            throw new CustomException("không tìm thấy sản phẩm", HttpStatus.NOT_FOUND);
        }
    }

    public Product toEntity(ProductRequest productRequest) throws CustomException
    {
        return Product.builder()
                .name(productRequest.getName())
                .image(productRequest.getImage())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .category(categoryService.findById(productRequest.getCategoryId()))
                .build();
    }
}
