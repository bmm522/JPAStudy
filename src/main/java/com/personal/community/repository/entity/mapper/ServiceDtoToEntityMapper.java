package com.personal.community.repository.entity.mapper;

import com.personal.community.repository.entity.Board;
import com.personal.community.repository.entity.util.Status;
import com.personal.community.service.dto.request.ServiceSaveBoardRequestDto;

public class ServiceDtoToEntityMapper {

    private static ServiceDtoToEntityMapper serviceDtoToEntityMapper = new ServiceDtoToEntityMapper();

    private ServiceDtoToEntityMapper(){}

    public static ServiceDtoToEntityMapper getInstance(){
        return serviceDtoToEntityMapper;
    }

    public Board toEntityWhenSave(ServiceSaveBoardRequestDto dto){
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(Status.Y)
                .build();
    }
}
