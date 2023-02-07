package com.callbus.community.controller.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientMemberRequestDto {

    private Long memberId;
    private String accountType;

    @Builder
    public ClientMemberRequestDto(String memberId, String accountType){
        this.memberId = Long.parseLong(memberId);
        this.accountType = accountType;
    }
}
