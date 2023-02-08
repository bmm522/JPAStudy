package com.callbus.community.service.dto.request;

import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.domain.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ServiceBoardUpdateReqeustDto {

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

    public ServiceBoardUpdateReqeustDto(Long boardId, ClientBoardUpdateRequestDto clientBoardUpdateRequestDto){
        this.boardId = boardId;
        this.title = clientBoardUpdateRequestDto.getTitle();
        this.content = clientBoardUpdateRequestDto.getContent();
    }

    @Builder
    public ServiceBoardUpdateReqeustDto(Long boardId, String title, String content, LocalDateTime updateDate) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
    }
}
