package com.personal.community.service.dto.request;

import com.personal.community.controller.dto.request.ClientSaveBoardRequestDto;
import com.personal.community.controller.dto.request.ClientMemberRequestDto;
import com.personal.community.domain.Board;
import com.personal.community.domain.util.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceSaveBoardRequestDto {

    private String title;

    private String content;

    private Long memberId;

    private String accountType;

    public Board toEntity(){
        return  Board.builder()
                .title(title)
                .content(content)
                .status(Status.Y)
                .build();
    }
    @Builder
    public ServiceSaveBoardRequestDto(String title, String content, Long memberId, String accountType){
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.accountType = accountType;
    }


    public ServiceSaveBoardRequestDto(ClientSaveBoardRequestDto clientSaveBoardRequestDto, ClientMemberRequestDto clientMemberRequestDto){
        this.title = clientSaveBoardRequestDto.getTitle();
        this.content = clientSaveBoardRequestDto.getContent();
        this.memberId = clientMemberRequestDto.getMemberId();
        this.accountType = clientMemberRequestDto.getAccountType();
    }


}
