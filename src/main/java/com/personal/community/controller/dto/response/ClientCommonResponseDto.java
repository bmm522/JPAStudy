package com.personal.community.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClientCommonResponseDto<T> {

    private Integer code; // 1. SUCCESS, -1 FAIL
    private String msg;
    private T body;

    @Builder
    public ClientCommonResponseDto(int code, String msg, T body ){
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

}
