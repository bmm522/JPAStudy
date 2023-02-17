package com.personal.community.service;

import com.personal.community.service.dto.request.ServiceSaveBoardSaveRequestDto;
import com.personal.community.service.dto.request.ServiceDeleteBoardRequestDto;
import com.personal.community.service.dto.request.ServiceGetBoardRequestDto;
import com.personal.community.service.dto.request.ServiceLikeReqeustDto;
import com.personal.community.service.dto.request.ServiceUpdateBoardReqeustDto;
import com.personal.community.service.dto.response.*;

public interface BoardService {



    public ServiceSaveBoardResponseDto saveBoard(ServiceSaveBoardSaveRequestDto dto);

    public ServiceUpdateBoardResponseDto updateBoard(ServiceUpdateBoardReqeustDto dto);

    public ServiceDeleteBoardResponseDto deleteBoard(ServiceDeleteBoardRequestDto dto);

    public ServiceLikeResponseDto saveLike(ServiceLikeReqeustDto dto);

    public ServiceLikeResponseDto cancleLike(ServiceLikeReqeustDto dto);

    public ServiceGetBoardListResponseDto getBoardList(ServiceGetBoardRequestDto dto);

    public ServiceGetBoardResponseDto getBoardDetails(ServiceGetBoardRequestDto dto);
}
