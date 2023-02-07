package com.callbus.community.service;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.response.BoardDeleteResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.service.dto.response.BoardUpdateResponseDto;

public interface BoardService {

    public ClientCommonResponseDto<?>  saveBoard(ServiceBoardSaveRequestDto serviceBoardSaveRequestDto);

    public BoardUpdateResponseDto updateBoard(Long boardId, ClientBoardUpdateRequestDto clientBoardUpdateRequestDto);

    public BoardDeleteResponseDto deleteBoard(Long boardId);
}
