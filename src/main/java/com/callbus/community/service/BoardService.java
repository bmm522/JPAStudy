package com.callbus.community.service;

import com.callbus.community.service.dto.request.ServiceGetBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceSaveBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceUpdateBoardReqeustDto;
import com.callbus.community.service.dto.request.ServiceLikeReqeustDto;
import com.callbus.community.service.dto.response.*;

public interface BoardService {

    public ServiceSaveBoardResponseDto saveBoard(ServiceSaveBoardRequestDto serviceSaveBoardRequestDto);

    public ServiceUpdateBoardResponseDto updateBoard(ServiceUpdateBoardReqeustDto serviceUpdateBoardReqeustDto);

    public ServiceDeleteBoardResponseDto deleteBoard(Long boardId);

    public ServiceLikeResponseDto saveLike(ServiceLikeReqeustDto serviceLikeReqeustDto);

    public ServiceLikeResponseDto cancleLike(ServiceLikeReqeustDto serviceLikeReqeustDto);

    public ServiceGetBoardListResponseDto getBoardList(ServiceGetBoardRequestDto serviceGetBoardRequestDto);
}
