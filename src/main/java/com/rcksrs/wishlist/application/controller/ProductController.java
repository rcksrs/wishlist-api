package com.rcksrs.wishlist.application.controller;

import com.rcksrs.wishlist.domain.dto.ProductRequest;
import com.rcksrs.wishlist.domain.dto.ProductResponse;
import com.rcksrs.wishlist.domain.usecase.DeleteProductUseCase;
import com.rcksrs.wishlist.domain.usecase.FindProductUseCase;
import com.rcksrs.wishlist.domain.usecase.SaveProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product Controller")
public class ProductController {
    private final FindProductUseCase findProductUseCase;
    private final SaveProductUseCase saveProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Retorna a lista de produtos na wishlist do usuário")
    public ResponseEntity<Set<ProductResponse>> findByUserId(@PathVariable String userId,
                                                             @RequestParam(required = false) String title) {
        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(findProductUseCase.findByTitle(userId, title));
        }
        return ResponseEntity.ok(findProductUseCase.findAll(userId));
    }

    @GetMapping("/user/{userId}/sku/{sku}")
    @Operation(summary = "Busca um produto na wishlist a partir do código SKU")
    public ResponseEntity<ProductResponse> findBySku(@PathVariable String userId, @PathVariable String sku) {
        return ResponseEntity.ok(findProductUseCase.findBySku(userId, sku));
    }

    @PostMapping("/user/{userId}")
    @Operation(summary = "Salva um produto na wishlist do usuário")
    public ResponseEntity<ProductResponse> save(@PathVariable String userId, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saveProductUseCase.save(userId, request));
    }

    @DeleteMapping("/user/{userId}/sku/{sku}")
    @Operation(summary = "Remove um produto da wishlist do usuário a partir do código SKU")
    public ResponseEntity<ProductResponse> delete(@PathVariable String userId, @PathVariable String sku) {
        deleteProductUseCase.delete(userId, sku);
        return ResponseEntity.noContent().build();
    }

}
