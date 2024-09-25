package com.ra.controller.admin;

import com.ra.dto.req.CategoryRequest;
import com.ra.dto.req.ProductRequest;
import com.ra.exception.CustomException;
import com.ra.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AProductController
{

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String search
    )
    {
        return ResponseEntity.ok().body(productService.findAll(pageable, search));
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> findById(@PathVariable Long productId) throws CustomException
    {
        return ResponseEntity.ok().body(productService.findById(productId));
    }


    @PostMapping
    public ResponseEntity<?> addNewCategory(@Valid @RequestBody ProductRequest productRequest) throws CustomException
    {
        return ResponseEntity.created(URI.create("api/v1/admin/products")).body(productService.save(productRequest));
    }


    @PutMapping("/{productId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody ProductRequest productRequest, @PathVariable Long productId) throws CustomException
    {
        return ResponseEntity.ok().body(productService.update(productRequest, productId)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteById(@PathVariable Long productId) throws CustomException
    {
        productService.deleteById(productId);
        return ResponseEntity.ok().body("Xóa sản phẩm thành công");
    }
}
