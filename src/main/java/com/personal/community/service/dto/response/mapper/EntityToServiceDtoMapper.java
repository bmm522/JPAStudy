package com.personal.community.service.dto.response.mapper;

import com.personal.community.repository.entity.Board;
import com.personal.community.service.dto.response.ServiceSaveBoardResponseDto;

public class EntityToServiceDtoMapper {
    private static final EntityToServiceDtoMapper entityToServiceDtoMapper = new EntityToServiceDtoMapper();

    private EntityToServiceDtoMapper(){}

    public static EntityToServiceDtoMapper getInstance(){return entityToServiceDtoMapper;}

    public ServiceSaveBoardResponseDto toSaveDto(Board board){
        return ServiceSaveBoardResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getMemberNickname())
                .memberId(board.getMemberId())
                .createDate(board.getCreateDate())
                .build();
    }
}
