package com.personal.community.service.dto.request;

import com.personal.community.controller.dto.request.ClientMemberRequestDto;
import com.personal.community.repository.entity.util.AccountType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceGetBoardRequestDto {

    private Long memberId;

    private Long boardId;

    private AccountType accountType;


    @Builder
    public ServiceGetBoardRequestDto(Long memberId, Long boardId, AccountType accountType) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.accountType = accountType;
    }

    public ServiceGetBoardRequestDto(ClientMemberRequestDto clientMemberRequestDto) {
        this.memberId = clientMemberRequestDto.getMemberId();
        this.accountType = getAccountType(clientMemberRequestDto.getAccountType());
    }

    public ServiceGetBoardRequestDto(Long boardId,ClientMemberRequestDto clientMemberRequestDto) {
        this.memberId = clientMemberRequestDto.getMemberId();
        this.boardId = boardId;
        this.accountType = getAccountType(clientMemberRequestDto.getAccountType());

    }

    private AccountType getAccountType(String accountType){
        switch (accountType){
            case "Realtor" : return AccountType.Realtor;
            case "Lessor" : return AccountType.Lessor;
            case "Lessee" : return AccountType.Lessee;
            default: return AccountType.externalUser;
        }
    }
}
