package ru.tiunov.homeworkapp.exceptions;

public class NotFoundElementException extends Exception{
    public NotFoundElementException() {
    }

    public NotFoundElementException(String message) {
        super(message);
    }
}
