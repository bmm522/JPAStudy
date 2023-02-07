package com.callbus.community.controller.dto.request;


import com.callbus.community.domain.util.AccountType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberReqDto {

    private Long memberId;
    private String accountType;

    @Builder
    public MemberReqDto(String memberId, String accountType){
        this.memberId = Long.parseLong(memberId);
        this.accountType = accountType;
    }
}
