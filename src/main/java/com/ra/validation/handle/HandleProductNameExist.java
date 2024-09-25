package com.ra.validation.handle;

import com.ra.repository.IProductRepository;
import com.ra.validation.annotation.ProductNameExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandleProductNameExist implements ConstraintValidator<ProductNameExist,String>
{
    private final IProductRepository productRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext)
    {
        return !productRepository.existsByName(s);
    }
}
