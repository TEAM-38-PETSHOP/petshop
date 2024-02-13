package org.globaroman.petshopba.exception;

public class EntityNotFoundCustomException extends RuntimeException {
    public EntityNotFoundCustomException(String message) {
        super(message);
    }
}
