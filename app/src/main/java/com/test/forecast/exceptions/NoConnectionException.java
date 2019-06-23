package com.test.forecast.exceptions;

public class NoConnectionException extends RuntimeException {
    public NoConnectionException() {
        super("No internet connection :(");
    }
}
