package com.callbus.community.service;

import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardUpdateReqeustDto;

public interface BoardService {

    public ClientCommonResponseDto<?>  saveBoard(ServiceBoardSaveRequestDto serviceBoardSaveRequestDto);

    public ClientCommonResponseDto<?> updateBoard(ServiceBoardUpdateReqeustDto serviceBoardUpdateReqeustDto);

    public ClientCommonResponseDto<?> deleteBoard(Long boardId);
}
