package com.personal.community.controller.dto.request;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientMemberRequestDto {

    @ApiModelProperty(hidden = true)
    private Long memberId;
    @ApiModelProperty(hidden = true)
    private String accountType;

    @Builder // Test용 생성자
    public ClientMemberRequestDto(String memberId, String accountType){
        this.memberId = Long.parseLong(memberId);
        this.accountType = accountType;
    }
}
