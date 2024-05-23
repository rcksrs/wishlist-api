package com.rcksrs.wishlist.domain.exception;

import com.rcksrs.wishlist.domain.exception.shared.BusinessException;

public class WishlistLimitExceededException extends BusinessException {
    public WishlistLimitExceededException() {
        super("The maximum limit of items in the list has been reached");
    }
}
