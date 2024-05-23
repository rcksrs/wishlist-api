package com.rcksrs.wishlist.application.usecase;

import com.rcksrs.wishlist.domain.entity.Product;
import com.rcksrs.wishlist.domain.entity.Wishlist;
import com.rcksrs.wishlist.domain.exception.ProductNotFoundException;
import com.rcksrs.wishlist.domain.exception.WishlistNotFoundException;
import com.rcksrs.wishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindProductUseCaseImplTest {
    private static final String USER_ID = "userId";
    private static final String PRODUCT_ID = "productId";
    private static final String TITLE = "title";

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private FindProductUseCaseImpl findProductUseCase;

    @Test
    @DisplayName("Deve retornar um produto")
    void shouldFindProduct() {
        var wishlist = new Wishlist();
        wishlist.setProducts(Collections.singleton(new Product(PRODUCT_ID)));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        var response = findProductUseCase.findBySku(USER_ID, PRODUCT_ID);
        assertNotNull(response);
        assertEquals(PRODUCT_ID, response.sku());

        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Não deve retornar um produto caso o usuário não exista")
    void shouldNotFindProductWhenUserNotExists() {
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        assertThrows(WishlistNotFoundException.class, () -> findProductUseCase.findBySku(USER_ID, PRODUCT_ID));
        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Não deve retornar um produto caso o produto não exista")
    void shouldNotFindProductWhenProductNotExists() {
        var wishlist = new Wishlist();
        wishlist.setProducts(Collections.emptySet());

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        assertThrows(ProductNotFoundException.class, () -> findProductUseCase.findBySku(USER_ID, PRODUCT_ID));
        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Deve retornar um produto ao buscar pelo título")
    void shouldFindProductByTitle() {
        var wishlist = new Wishlist();
        var product = Product.builder()
                .sku(PRODUCT_ID)
                .title(TITLE)
                .build();
        wishlist.setProducts(Collections.singleton(product));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        var response = findProductUseCase.findByTitle(USER_ID, TITLE);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(PRODUCT_ID, response.stream().toList().get(0).sku());
        assertEquals(TITLE, response.stream().toList().get(0).title());

        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia caso o produto não seja encontrado")
    void shouldNotFindProductByTitleWhenProductNotExists() {
        var wishlist = new Wishlist();
        var product = Product.builder()
                .sku(PRODUCT_ID)
                .title(TITLE)
                .build();
        wishlist.setProducts(Collections.singleton(product));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        var response = findProductUseCase.findByTitle(USER_ID, "test");
        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Não deve retornar um produto ao buscar pelo título caso o usuário não exista")
    void shouldNotFindProductByTitleWhenUserNotExists() {
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        assertThrows(WishlistNotFoundException.class, () -> findProductUseCase.findByTitle(USER_ID, TITLE));
        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Deve retornar todos os produtos da wishlist do usuário")
    void shouldFindAllProducts() {
        var wishlist = new Wishlist();
        wishlist.setProducts(Collections.singleton(new Product(PRODUCT_ID)));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        var response = findProductUseCase.findAll(USER_ID);
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(PRODUCT_ID, response.stream().toList().get(0).sku());

        verify(wishlistRepository).findByUserId(USER_ID);
    }

    @Test
    @DisplayName("Não deve retornar a lista de produtos caso o usuário não exista")
    void shouldNotFindProductsWhenUserNotExists() {
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        assertThrows(WishlistNotFoundException.class, () -> findProductUseCase.findAll(USER_ID));
        verify(wishlistRepository).findByUserId(USER_ID);
    }

}