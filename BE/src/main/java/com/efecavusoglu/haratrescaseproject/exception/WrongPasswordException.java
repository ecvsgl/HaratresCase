package com.efecavusoglu.haratrescaseproject.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(String msg){
        super(msg);
    }
}