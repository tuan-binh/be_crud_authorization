package com.ra.controller.all;

import com.ra.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController
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
}
