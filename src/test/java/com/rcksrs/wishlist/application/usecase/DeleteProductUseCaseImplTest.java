package com.rcksrs.wishlist.application.usecase;

import com.rcksrs.wishlist.domain.entity.Product;
import com.rcksrs.wishlist.domain.entity.Wishlist;
import com.rcksrs.wishlist.domain.exception.ProductNotFoundException;
import com.rcksrs.wishlist.domain.exception.WishlistNotFoundException;
import com.rcksrs.wishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseImplTest {
    private static final String USER_ID = "userId";
    private static final String PRODUCT_ID = "productId";

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private DeleteProductUseCaseImpl deleteProductUseCase;

    @Test
    @DisplayName("Deve remover um produto da wishlist")
    void shouldDeleteProduct() {
        var wishlist = new Wishlist();
        wishlist.setProducts(new HashSet<>());
        wishlist.getProducts().add(new Product(PRODUCT_ID));
        wishlist.getProducts().add(new Product(PRODUCT_ID + 1));

        var captor = ArgumentCaptor.forClass(Wishlist.class);
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(captor.capture())).thenReturn(wishlist);

        deleteProductUseCase.delete(USER_ID, PRODUCT_ID + 1);
        var wishlistSaved = captor.getValue();

        assertNotNull(wishlistSaved);
        assertEquals(1, wishlistSaved.getProducts().size());
        assertEquals(PRODUCT_ID, wishlistSaved.getProducts().stream().toList().get(0).getSku());

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("Não deve remover um produto da wishlist caso não seja encontrado")
    void shouldNotDeleteProductWhenProductNotExists() {
        var wishlist = new Wishlist();
        wishlist.setProducts(new HashSet<>());
        wishlist.getProducts().add(new Product(PRODUCT_ID));
        wishlist.getProducts().add(new Product(PRODUCT_ID + 1));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        assertThrows(ProductNotFoundException.class, () -> deleteProductUseCase.delete(USER_ID, PRODUCT_ID + 2));

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve remover um produto da wishlist caso o usuário não exista")
    void shouldNotDeleteProductWhenUserNotExists() {
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class, () -> deleteProductUseCase.delete(USER_ID, PRODUCT_ID));

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository, never()).save(any());
    }

}