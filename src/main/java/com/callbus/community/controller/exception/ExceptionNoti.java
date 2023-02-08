package com.callbus.community.controller.exception;

import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.controller.dto.response.Code;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionNoti {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e){
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.FAIL.getCode()).msg(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.FAIL.getCode()).msg(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}
