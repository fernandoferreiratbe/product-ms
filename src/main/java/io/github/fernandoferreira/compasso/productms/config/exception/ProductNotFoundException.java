package io.github.fernandoferreira.compasso.productms.config.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public ProductNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
