package com.callbus.community.service;

import com.callbus.community.domain.Board;
import com.callbus.community.service.dto.ServiceRequestDto;
import com.callbus.community.service.dto.request.*;
import com.callbus.community.service.dto.response.*;

public interface BoardService {



    public ServiceSaveBoardResponseDto saveBoard(ServiceRequestDto dto);

    public ServiceUpdateBoardResponseDto updateBoard(ServiceRequestDto dto);

    public ServiceDeleteBoardResponseDto deleteBoard(ServiceRequestDto dto);

    public ServiceLikeResponseDto saveLike(ServiceRequestDto dto);

    public ServiceLikeResponseDto cancleLike(ServiceRequestDto dto);

    public ServiceGetBoardListResponseDto getBoardList(ServiceRequestDto dto);

    public ServiceGetBoardResponseDto getBoardDetails(ServiceRequestDto dto);
}
