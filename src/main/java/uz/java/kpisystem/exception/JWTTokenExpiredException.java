package uz.java.kpisystem.exception;

public class JWTTokenExpiredException extends RuntimeException {
    public JWTTokenExpiredException(String message) {
        super(message);
    }
}
