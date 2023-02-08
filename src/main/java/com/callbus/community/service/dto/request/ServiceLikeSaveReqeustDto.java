package com.callbus.community.service.dto.request;

import com.callbus.community.controller.dto.request.ClientLikeSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.domain.Like;
import lombok.Getter;

@Getter
public class ServiceLikeSaveReqeustDto {

    private Long boardId;
    private Long memberId;

    public ServiceLikeSaveReqeustDto(ClientLikeSaveRequestDto clientLikeSaveRequestDto, ClientMemberRequestDto clientMemberRequestDto){
        this.boardId = clientLikeSaveRequestDto.getBoardId();
        this.memberId = clientMemberRequestDto.getMemberId();
    }

    public Like toEntity(){
        return new Like();
    }

}
