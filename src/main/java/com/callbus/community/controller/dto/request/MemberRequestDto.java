package com.callbus.community.controller.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberRequestDto {

    private Long memberId;
    private String accountType;

    @Builder
    public MemberRequestDto(String memberId, String accountType){
        this.memberId = Long.parseLong(memberId);
        this.accountType = accountType;
    }
}
