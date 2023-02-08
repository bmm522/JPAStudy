package com.callbus.community.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientLikeSaveRequestDto {

    private Long boardId;

    @Builder // Test용 생성자
    public ClientLikeSaveRequestDto(Long boardId){
        this.boardId = boardId;
    }
}
