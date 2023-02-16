package com.personal.community.service;

import com.personal.community.service.dto.ServiceRequestDto;
import com.personal.community.service.dto.response.*;

public interface BoardService {



    public ServiceSaveBoardResponseDto saveBoard(ServiceRequestDto dto);

    public ServiceUpdateBoardResponseDto updateBoard(ServiceRequestDto dto);

    public ServiceDeleteBoardResponseDto deleteBoard(ServiceRequestDto dto);

    public ServiceLikeResponseDto saveLike(ServiceRequestDto dto);

    public ServiceLikeResponseDto cancleLike(ServiceRequestDto dto);

    public ServiceGetBoardListResponseDto getBoardList(ServiceRequestDto dto);

    public ServiceGetBoardResponseDto getBoardDetails(ServiceRequestDto dto);
}
