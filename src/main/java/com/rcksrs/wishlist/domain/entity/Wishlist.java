package com.rcksrs.wishlist.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Document(collection = "wishlists")
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    private String id;
    private String userId;
    private Set<Product> products;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Wishlist(String userId, Product product) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.products = Collections.singleton(product);
    }
}
