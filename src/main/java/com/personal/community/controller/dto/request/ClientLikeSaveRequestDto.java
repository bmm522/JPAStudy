package com.personal.community.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
public class ClientLikeSaveRequestDto {

    private Long boardId;

    @Builder // Test용 생성자
    public ClientLikeSaveRequestDto(Long boardId){
        this.boardId = boardId;
    }

    public ClientLikeSaveRequestDto(){}

    public Long getBoardId(){
        return boardId;
    }
}
