package com.personal.community.service.dto.request;

import com.personal.community.controller.dto.request.ClientMemberRequestDto;
import com.personal.community.controller.dto.request.ClientUpdateBoardRequestDto;
import com.personal.community.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ServiceUpdateBoardReqeustDto {

    private Long boardId;

    private String title;

    private String content;

    private Long memberId;

    private LocalDateTime updateDate;

    public Board toEntity(){
        return  Board.builder()
               .title(title)
               .content(content)
               .build();
    }

    public ServiceUpdateBoardReqeustDto(Long boardId, ClientUpdateBoardRequestDto clientUpdateBoardRequestDto, ClientMemberRequestDto clientMemberRequestDto){
        this.boardId = boardId;
        this.title = clientUpdateBoardRequestDto.getTitle();
        this.content = clientUpdateBoardRequestDto.getContent();
        this.memberId = clientMemberRequestDto.getMemberId();
    }

    @Builder
    public ServiceUpdateBoardReqeustDto(Long boardId, String title, String content, LocalDateTime updateDate, Long memberId) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
        this.memberId = memberId;
    }



}
