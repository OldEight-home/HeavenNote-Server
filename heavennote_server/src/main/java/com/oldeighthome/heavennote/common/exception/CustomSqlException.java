package com.oldeighthome.heavennote.common.exception;

public class CustomSqlException extends RuntimeException{
    public CustomSqlException() {
        super();
    }

    public CustomSqlException(String message) {
        super(message);
    }
}
