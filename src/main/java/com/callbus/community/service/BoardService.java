package com.callbus.community.service;

import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardUpdateReqeustDto;
import com.callbus.community.service.dto.request.ServiceLikeSaveReqeustDto;
import com.callbus.community.service.dto.response.ServiceBoardDeleteResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardUpdateResponseDto;
import com.callbus.community.service.dto.response.ServiceLikeResponseDto;

public interface BoardService {

    public ServiceBoardSaveResponseDto saveBoard(ServiceBoardSaveRequestDto serviceBoardSaveRequestDto);

    public ServiceBoardUpdateResponseDto updateBoard(ServiceBoardUpdateReqeustDto serviceBoardUpdateReqeustDto);

    public ServiceBoardDeleteResponseDto deleteBoard(Long boardId);

    public ServiceLikeResponseDto saveLike(ServiceLikeSaveReqeustDto serviceLikeSaveReqeustDto);
}
