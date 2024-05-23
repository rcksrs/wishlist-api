package com.rcksrs.wishlist.application.controller;

import com.rcksrs.wishlist.domain.dto.ProductRequest;
import com.rcksrs.wishlist.domain.dto.ProductResponse;
import com.rcksrs.wishlist.domain.usecase.DeleteProductUseCase;
import com.rcksrs.wishlist.domain.usecase.FindProductUseCase;
import com.rcksrs.wishlist.domain.usecase.SaveProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final FindProductUseCase findProductUseCase;
    private final SaveProductUseCase saveProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<ProductResponse>> findByUserId(@PathVariable String userId,
                                                             @RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(findProductUseCase.findByTitle(userId, title));
        }
        return ResponseEntity.ok(findProductUseCase.findAll(userId));
    }

    @GetMapping("/user/{userId}/sku/{sku}")
    public ResponseEntity<ProductResponse> findBySku(@PathVariable String userId, @PathVariable String sku) {
        return ResponseEntity.ok(findProductUseCase.findBySku(userId, sku));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ProductResponse> save(@PathVariable String userId, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(saveProductUseCase.save(userId, request));
    }

    @DeleteMapping("/user/{userId}/sku/{sku}")
    public ResponseEntity<ProductResponse> delete(@PathVariable String userId, @PathVariable String sku) {
        deleteProductUseCase.delete(userId, sku);
        return ResponseEntity.noContent().build();
    }

}
