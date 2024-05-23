package com.rcksrs.wishlist.domain.dto;

import com.rcksrs.wishlist.domain.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(String sku, String brand, String title, String description, BigDecimal price) {
    public Product toEntity() {
        return Product.builder()
                .id(UUID.randomUUID().toString())
                .sku(this.sku)
                .brand(this.brand)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .build();
    }
}
