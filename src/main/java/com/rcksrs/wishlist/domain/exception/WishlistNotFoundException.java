package com.rcksrs.wishlist.domain.exception;

import com.rcksrs.wishlist.domain.exception.shared.EntityNotFoundException;

public class WishlistNotFoundException extends EntityNotFoundException {
    public WishlistNotFoundException() {
        super("Wishlist not found");
    }
}
