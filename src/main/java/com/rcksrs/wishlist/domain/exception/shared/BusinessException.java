package com.rcksrs.wishlist.domain.exception.shared;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
