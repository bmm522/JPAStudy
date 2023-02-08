package com.callbus.community.service.dto.request;

import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.domain.util.AccountType;
import lombok.Getter;

@Getter
public class ServiceGetBoardRequestDto {

    private Long memberId;

    private AccountType accountType;

    public ServiceGetBoardRequestDto(ClientMemberRequestDto clientMemberRequestDto) {
        memberId = clientMemberRequestDto.getMemberId();
        switch (clientMemberRequestDto.getAccountType()){
            case "Realtor" : this.accountType = AccountType.Realtor; break;
            case "Lessor" : this.accountType = AccountType.Lessor; break;
            case "Lessee" : this.accountType = AccountType.Lessee; break;
            default: this.accountType = AccountType.externalUser; break;
        }
    }
}
