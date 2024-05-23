package com.rcksrs.wishlist.application.usecase;

import com.rcksrs.wishlist.domain.entity.Product;
import com.rcksrs.wishlist.domain.exception.ProductNotFoundException;
import com.rcksrs.wishlist.domain.exception.WishlistNotFoundException;
import com.rcksrs.wishlist.domain.repository.WishlistRepository;
import com.rcksrs.wishlist.domain.usecase.DeleteProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {
    private final WishlistRepository wishlistRepository;

    @Override
    public void delete(String userId, String sku) {
        var wishlist = wishlistRepository.findByUserId(userId).orElseThrow(WishlistNotFoundException::new);
        var product = new Product(sku);

        if (!wishlist.getProducts().contains(product)) throw new ProductNotFoundException();

        var products = wishlist.getProducts()
                .stream()
                .filter(p -> !p.equals(product))
                .collect(Collectors.toSet());
        wishlist.setProducts(products);
        wishlist.setModifiedAt(LocalDateTime.now());
        wishlistRepository.save(wishlist);
    }
}
