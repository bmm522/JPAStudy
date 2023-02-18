package com.personal.community.service.dto.request.mapper;

import com.personal.community.controller.dto.request.ClientMemberRequestDto;
import com.personal.community.controller.dto.request.ClientSaveBoardRequestDto;
import com.personal.community.service.dto.request.ServiceSaveBoardRequestDto;

public class ControllerDtoToServiceDtoMapper {

    private static final ControllerDtoToServiceDtoMapper controllerDtoToServiceDtoMapper = new ControllerDtoToServiceDtoMapper();

    private ControllerDtoToServiceDtoMapper(){}

    public static ControllerDtoToServiceDtoMapper getInstance() {return controllerDtoToServiceDtoMapper;}

    public ServiceSaveBoardRequestDto toServiceDtoWhenSave(ClientSaveBoardRequestDto clientSaveBoardRequestDto, ClientMemberRequestDto clientMemberRequestDto){
        return ServiceSaveBoardRequestDto.builder()
                .title(clientSaveBoardRequestDto.getTitle())
                .content(clientSaveBoardRequestDto.getContent())
                .memberId(clientMemberRequestDto.getMemberId())
                .accountType(clientMemberRequestDto.getAccountType())
                .build();

    }
}
