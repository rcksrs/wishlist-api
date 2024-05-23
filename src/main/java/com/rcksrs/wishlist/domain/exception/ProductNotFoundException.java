package com.rcksrs.wishlist.domain.exception;

import com.rcksrs.wishlist.domain.exception.shared.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
