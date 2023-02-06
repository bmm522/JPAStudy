package com.callbus.community.controller.dto.response.util;

public enum ResponseCode {
    SUCCESS(1), FAIL(-1);

    private final Integer code;

    ResponseCode(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }
}
