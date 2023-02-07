package com.callbus.community.service.dto.request;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ServiceBoardSaveRequestDto {

    private String title;

    private String content;

    private Long memberId;

    private String accountType;

    public Board toEntity(){
        return  Board.builder()
                .title(title)
                .content(content)
                .build();
    }

    public ServiceBoardSaveRequestDto(ClientBoardSaveRequestDto clientBoardSaveRequestDto, ClientMemberRequestDto clientMemberRequestDto){
        this.title = clientBoardSaveRequestDto.getTitle();
        this.content = clientBoardSaveRequestDto.getContent();
        this.memberId = clientMemberRequestDto.getMemberId();
        this.accountType = clientMemberRequestDto.getAccountType();
    }

    @Builder
    public ServiceBoardSaveRequestDto(String title, String content, Long memberId, String accountType){
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.accountType = accountType;
    }
}
