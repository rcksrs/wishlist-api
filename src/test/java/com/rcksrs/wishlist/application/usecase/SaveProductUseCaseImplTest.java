package com.rcksrs.wishlist.application.usecase;

import com.rcksrs.wishlist.domain.dto.ProductRequest;
import com.rcksrs.wishlist.domain.entity.Product;
import com.rcksrs.wishlist.domain.entity.Wishlist;
import com.rcksrs.wishlist.domain.exception.WishlistLimitExceededException;
import com.rcksrs.wishlist.domain.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class SaveProductUseCaseImplTest {
    private static final String USER_ID = "userId";
    private static final String PRODUCT_ID = "productId";

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private SaveProductUseCaseImpl saveProductUseCase;

    @BeforeEach
    void setUp() {
        setField(saveProductUseCase, "maxProductItems", 20);
    }

    @Test
    @DisplayName("Deve salvar um produto")
    void shouldSaveProduct() {
        var wishlist = new Wishlist();
        wishlist.setProducts(new HashSet<>());

        var captor = ArgumentCaptor.forClass(Wishlist.class);
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(captor.capture())).thenReturn(wishlist);

        var request = new ProductRequest(PRODUCT_ID, null, null, null, null);
        var response = saveProductUseCase.save(USER_ID, request);
        var wishlistSaved = captor.getValue();

        assertNotNull(response);
        assertNotNull(wishlistSaved);

        assertEquals(1, wishlistSaved.getProducts().size());
        assertEquals(PRODUCT_ID, wishlistSaved.getProducts().stream().toList().get(0).getSku());

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("Deve salvar um produto na wishlist e criar a wishlist")
    void shouldSaveProductAndCreateWishlist() {
        var captor = ArgumentCaptor.forClass(Wishlist.class);
        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        when(wishlistRepository.save(captor.capture())).thenReturn(new Wishlist());

        var request = new ProductRequest(PRODUCT_ID, null, null, null, null);
        var response = saveProductUseCase.save(USER_ID, request);
        var wishlistSaved = captor.getValue();

        assertNotNull(response);
        assertNotNull(wishlistSaved);

        assertEquals(1, wishlistSaved.getProducts().size());
        assertEquals(PRODUCT_ID, wishlistSaved.getProducts().stream().toList().get(0).getSku());

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("Não deve salvar um produto repetido na lista")
    void shouldNotSaveProductWhenProductExists() {
        var wishlist = new Wishlist();
        wishlist.setProducts(new HashSet<>());
        wishlist.getProducts().add(new Product(PRODUCT_ID));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));


        var request = new ProductRequest(PRODUCT_ID, null, null, null, null);
        var response = saveProductUseCase.save(USER_ID, request);

        assertNotNull(response);

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

    @Test
    @DisplayName("Não deve salvar um produto caso a lista esteja com a quantidade de máxima de itens")
    void shouldNotSaveProductWhenLimitWasReached() {
        setField(saveProductUseCase, "maxProductItems", 2);

        var wishlist = new Wishlist();
        wishlist.setProducts(new HashSet<>());
        wishlist.getProducts().add(new Product(PRODUCT_ID + 1));
        wishlist.getProducts().add(new Product(PRODUCT_ID + 2));

        when(wishlistRepository.findByUserId(anyString())).thenReturn(Optional.of(wishlist));

        var request = new ProductRequest(PRODUCT_ID, null, null, null, null);
        assertThrows(WishlistLimitExceededException.class, () -> saveProductUseCase.save(USER_ID, request));

        verify(wishlistRepository).findByUserId(USER_ID);
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }

}