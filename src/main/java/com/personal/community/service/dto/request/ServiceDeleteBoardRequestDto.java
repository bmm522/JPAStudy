package com.personal.community.service.dto.request;

import com.personal.community.controller.dto.request.ClientMemberRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceDeleteBoardRequestDto {

    private Long boardId;
    private Long memberId;



    @Builder
    public ServiceDeleteBoardRequestDto(Long boardId, Long memberId) {
        this.boardId = boardId;
        this.memberId = memberId;
    }

    public ServiceDeleteBoardRequestDto(Long boardId, ClientMemberRequestDto clientMemberRequestDto) {
        this.boardId = boardId;
        this.memberId = clientMemberRequestDto.getMemberId();
    }
}
