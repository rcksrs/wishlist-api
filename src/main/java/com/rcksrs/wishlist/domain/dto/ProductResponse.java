package com.rcksrs.wishlist.domain.dto;

import com.rcksrs.wishlist.domain.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(String id, String sku, String brand, String title, String description, BigDecimal price) {
    public ProductResponse(Product product) {
        this(product.getId(),
                product.getSku(),
                product.getBrand(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
