package com.rcksrs.wishlist.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {
    private String id;
    private String sku;
    private String brand;
    private String title;
    private String description;
    private BigDecimal price;
}
