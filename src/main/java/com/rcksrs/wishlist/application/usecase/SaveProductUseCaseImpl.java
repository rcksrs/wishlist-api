package com.rcksrs.wishlist.application.usecase;

import com.rcksrs.wishlist.domain.dto.ProductRequest;
import com.rcksrs.wishlist.domain.dto.ProductResponse;
import com.rcksrs.wishlist.domain.entity.Wishlist;
import com.rcksrs.wishlist.domain.exception.WishlistLimitExceededException;
import com.rcksrs.wishlist.domain.repository.WishlistRepository;
import com.rcksrs.wishlist.domain.usecase.SaveProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveProductUseCaseImpl implements SaveProductUseCase {
    private final WishlistRepository wishlistRepository;

    @Value("${app.data.max-product-items}")
    private Integer maxProductItems;

    @Override
    public ProductResponse save(String userId, ProductRequest request) {
        var product = request.toEntity();
        var wishlist = wishlistRepository.findByUserId(userId);

        if (wishlist.isEmpty()) {
            wishlistRepository.save(new Wishlist(userId, product));
            return new ProductResponse(product);
        }

        var products = wishlist.get().getProducts();
        if (products.size() >= maxProductItems) throw new WishlistLimitExceededException();
        if (products.add(product)) {
            wishlistRepository.save(wishlist.get());
            return new ProductResponse(product);
        }

        return products.stream()
                .filter(p -> p.equals(product))
                .findFirst()
                .map(ProductResponse::new)
                .orElse(null);
    }
}
