package com.efecavusoglu.haratrescaseproject.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    public ResponseEntity<ApiExceptionTemplate> handleException(UsernameAlreadyExistsException e){
        ApiExceptionTemplate template = getApiExceptionTemplate(e.getMessage());
        return new ResponseEntity<>(template, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({UnauthorizedOperationException.class})
    public ResponseEntity<ApiExceptionTemplate> handleException(UnauthorizedOperationException e){
        ApiExceptionTemplate template = getApiExceptionTemplate(e.getMessage());
        return new ResponseEntity<>(template, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({WrongPasswordException.class})
    public ResponseEntity<ApiExceptionTemplate> handleException(WrongPasswordException e){
        ApiExceptionTemplate template = getApiExceptionTemplate(e.getMessage());
        return new ResponseEntity<>(template, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({WrongUsernameException.class})
    public ResponseEntity<ApiExceptionTemplate> handleException(WrongUsernameException e){
        ApiExceptionTemplate template = getApiExceptionTemplate(e.getMessage());
        return new ResponseEntity<>(template, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({TaskNotFoundException.class})
    public ResponseEntity<ApiExceptionTemplate> handleException(TaskNotFoundException e){
        ApiExceptionTemplate template = getApiExceptionTemplate(e.getMessage());
        return new ResponseEntity<>(template, HttpStatus.NOT_FOUND);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ApiExceptionTemplate{
        private String exceptionMessage;
        private LocalDateTime exceptionDate;
    }

    private ApiExceptionTemplate getApiExceptionTemplate(String exceptionMessage){
        ApiExceptionTemplate template = new ApiExceptionTemplate();
        template.setExceptionMessage(exceptionMessage);
        template.setExceptionDate(LocalDateTime.now());
        return template;
    }
}
