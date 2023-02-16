package com.personal.community.service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ServiceGetBoardListResponseDto {

    List<ServiceGetBoardResponseDto> items;

    @Builder
    public ServiceGetBoardListResponseDto(List<ServiceGetBoardResponseDto> serviceGetBoardResponseDtos){
        this.items = serviceGetBoardResponseDtos;
    }
}
