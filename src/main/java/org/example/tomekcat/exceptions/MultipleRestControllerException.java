package org.example.tomekcat.exceptions;

public class MultipleRestControllerException extends RuntimeException {
    public MultipleRestControllerException(String message){
        super(message);
    }

}
