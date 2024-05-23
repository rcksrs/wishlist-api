package com.rcksrs.wishlist.domain.usecase;

import com.rcksrs.wishlist.domain.dto.ProductRequest;
import com.rcksrs.wishlist.domain.dto.ProductResponse;

public interface SaveProductUseCase {
    ProductResponse save(String userId, ProductRequest request);
}
