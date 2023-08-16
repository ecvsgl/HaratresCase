package com.efecavusoglu.haratrescaseproject.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String msg){
        super(msg);
    }
}
