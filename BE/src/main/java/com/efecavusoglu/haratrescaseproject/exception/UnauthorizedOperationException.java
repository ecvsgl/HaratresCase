package com.efecavusoglu.haratrescaseproject.exception;

public class UnauthorizedOperationException extends RuntimeException{
    public UnauthorizedOperationException(String msg){
        super(msg);
    }
}
