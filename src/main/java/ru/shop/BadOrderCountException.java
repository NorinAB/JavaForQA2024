package ru.shop;

public class BadOrderCountException extends RuntimeException {

    public BadOrderCountException() {
        super("Incorrect order count");
    }

}
