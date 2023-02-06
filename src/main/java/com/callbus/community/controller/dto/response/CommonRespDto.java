package com.callbus.community.controller.dto.response;

import com.callbus.community.controller.dto.response.util.ResponseCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonRespDto<T> {

    private ResponseCode code; // 1. SUCCESS, -1 FAIL
    private String msg;

    private T body;

    @Builder
    public CommonRespDto(ResponseCode code, String msg, T body ){
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

}
