package com.rcksrs.wishlist.domain.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(of = "sku")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String sku;
    private String brand;
    private String title;
    private String description;
    private BigDecimal price;

    public Product(String sku) {
        this.sku = sku;
    }
}
