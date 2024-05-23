package com.rcksrs.wishlist.application.usecase;

import com.rcksrs.wishlist.domain.dto.ProductResponse;
import com.rcksrs.wishlist.domain.exception.ProductNotFoundException;
import com.rcksrs.wishlist.domain.exception.WishlistNotFoundException;
import com.rcksrs.wishlist.domain.repository.WishlistRepository;
import com.rcksrs.wishlist.domain.usecase.FindProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindProductUseCaseImpl implements FindProductUseCase {
    private final WishlistRepository wishlistRepository;

    @Override
    public ProductResponse findBySku(String userId, String sku) {
        var wishlist = wishlistRepository.findByUserId(userId).orElseThrow(WishlistNotFoundException::new);
        return wishlist.getProducts().stream()
                .filter(product -> product.getSku().equals(sku))
                .findFirst()
                .map(ProductResponse::new)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public Set<ProductResponse> findAll(String userId) {
        var wishlist = wishlistRepository.findByUserId(userId).orElseThrow(WishlistNotFoundException::new);
        return wishlist.getProducts().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toSet());
    }
}
