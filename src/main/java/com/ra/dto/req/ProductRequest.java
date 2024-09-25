package com.ra.dto.req;

import com.ra.validation.annotation.ProductNameExist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "tên sản phẩm không được để trống")
    @ProductNameExist
    private String name;
    private String image;
    @Min(value = 1,message = "giá không được nhỏ hơn 0")
    private Double price;
    @Min(value = 1,message = "số lượng không được nhỏ hơn 0")
    private Integer quantity;
    private Long categoryId;
}
