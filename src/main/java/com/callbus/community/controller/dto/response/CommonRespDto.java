package com.callbus.community.controller.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonRespDto<T> {

    private Integer code; // 1. SUCCESS, -1 FAIL
    private String msg;

    private T body;

    @Builder
    public CommonRespDto(int code, String msg, T body ){
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

}