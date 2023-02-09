package com.callbus.community.service.dto.request;

import com.callbus.community.controller.dto.request.ClientLikeSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.domain.Like;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceLikeReqeustDto {

    private Long boardId;
    private Long memberId;

    public ServiceLikeReqeustDto(ClientLikeSaveRequestDto clientLikeSaveRequestDto, ClientMemberRequestDto clientMemberRequestDto){
        this.boardId = clientLikeSaveRequestDto.getBoardId();
        this.memberId = clientMemberRequestDto.getMemberId();
    }

    public ServiceLikeReqeustDto(Long boardId, ClientMemberRequestDto clientMemberRequestDto ){
        this.boardId = boardId;
        this.memberId = clientMemberRequestDto.getMemberId();
    }

    // Test용 생성자
    @Builder
    public ServiceLikeReqeustDto(Long boardId, Long memberId){
        this.boardId = boardId;
        this.memberId = memberId;
    }

    public Like toEntity(){
        return new Like();
    }

}
