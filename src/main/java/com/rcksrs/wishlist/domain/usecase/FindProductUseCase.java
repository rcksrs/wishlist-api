package com.rcksrs.wishlist.domain.usecase;

import com.rcksrs.wishlist.domain.dto.ProductResponse;

import java.util.List;

public interface FindProductUseCase {
    ProductResponse findById(String userId, String productId);
    List<ProductResponse> findAll(String userId);
}
