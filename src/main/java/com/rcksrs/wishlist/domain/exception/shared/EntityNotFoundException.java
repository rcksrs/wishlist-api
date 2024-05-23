package com.rcksrs.wishlist.domain.exception.shared;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
