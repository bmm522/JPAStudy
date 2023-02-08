package com.callbus.community.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ClientLikeSaveRequestDto {

    private String boardId;

    @Builder // Test용 생성자
    public ClientLikeSaveRequestDto(String boardId){
        this.boardId = boardId;
    }

    public ClientLikeSaveRequestDto(){}

    public Long getBoardId(){
        return Long.parseLong(boardId);
    }
}
