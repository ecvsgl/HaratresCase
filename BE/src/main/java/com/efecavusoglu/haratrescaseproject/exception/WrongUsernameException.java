package com.efecavusoglu.haratrescaseproject.exception;

public class WrongUsernameException extends  RuntimeException{
    public WrongUsernameException(String msg){
        super(msg);
    }
}