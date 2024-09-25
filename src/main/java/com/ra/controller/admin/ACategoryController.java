package com.ra.controller.admin;


import com.ra.dto.req.CategoryRequest;
import com.ra.exception.CustomException;
import com.ra.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class ACategoryController
{
    private final ICategoryService categoryService;

    /**
     * @param pageable Pageable
     * @param search   String
     * @apiNote handle get all category and pagination and search
     */
    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String search
    )
    {
        return ResponseEntity.ok().body(categoryService.findAll(pageable, search));
    }

    /**
     * @param categoryId Long
     * @throws CustomException throws because not found
     * @apiNote handle find by id category
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> findById(@PathVariable Long categoryId) throws CustomException
    {
        return ResponseEntity.ok().body(categoryService.findById(categoryId));
    }

    /**
     * @param categoryRequest CategoryRequest { categoryName }
     * @apiNote handle save new category and validation not null
     */
    @PostMapping
    public ResponseEntity<?> addNewCategory(@Valid @RequestBody CategoryRequest categoryRequest)
    {
        return ResponseEntity.created(URI.create("api/v1/admin/categories")).body(categoryService.save(categoryRequest));
    }

    /**
     * @param categoryRequest CategoryRequest { categoryName }
     * @param categoryId      Long
     * @throws CustomException throws because not found and is exist name
     * @apiNote handle update category by id and validation not null
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Long categoryId) throws CustomException
    {
        return ResponseEntity.ok().body(categoryService.update(categoryRequest, categoryId)
        );
    }

    /**
     * @param categoryId Long
     * @throws CustomException throws becouse not found id
     * @apiNote handle delete category by id
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteById(@PathVariable Long categoryId) throws CustomException
    {
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok().body("Xóa danh mục thành công");
    }

}
