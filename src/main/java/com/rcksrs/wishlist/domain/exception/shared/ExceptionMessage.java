package com.rcksrs.wishlist.domain.exception.shared;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionMessage(String message, String field) {
    public ExceptionMessage(String message) {
        this(message, null);
    }
}
