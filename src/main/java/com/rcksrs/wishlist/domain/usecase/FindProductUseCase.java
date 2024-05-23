package com.rcksrs.wishlist.domain.usecase;

import com.rcksrs.wishlist.domain.dto.ProductResponse;

import java.util.Set;

public interface FindProductUseCase {
    ProductResponse findBySku(String userId, String sku);
    Set<ProductResponse> findAll(String userId);
}
