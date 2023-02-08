package com.callbus.community.service.dto.request;

import com.callbus.community.controller.dto.request.ClientUpdateBoardRequestDto;
import com.callbus.community.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ServiceUpdateBoardReqeustDto {

    private Long boardId;

    private String title;

    private String content;

    private LocalDateTime updateDate;

    public Board toEntity(){
        return  Board.builder()
               .title(title)
               .content(content)
               .build();
    }

    public ServiceUpdateBoardReqeustDto(Long boardId, ClientUpdateBoardRequestDto clientUpdateBoardRequestDto){
        this.boardId = boardId;
        this.title = clientUpdateBoardRequestDto.getTitle();
        this.content = clientUpdateBoardRequestDto.getContent();
    }

    @Builder
    public ServiceUpdateBoardReqeustDto(Long boardId, String title, String content, LocalDateTime updateDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
    }
}
