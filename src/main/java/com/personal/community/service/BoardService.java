package com.personal.community.service;

import com.personal.community.service.dto.request.*;
import com.personal.community.service.dto.response.*;

public interface BoardService {



    public ServiceSaveBoardResponseDto saveBoard(ServiceSaveBoardRequestDto dto);

    public ServiceUpdateBoardResponseDto updateBoard(ServiceUpdateBoardReqeustDto dto);

    public ServiceDeleteBoardResponseDto deleteBoard(ServiceDeleteBoardRequestDto dto);

    public ServiceLikeResponseDto saveLike(ServiceLikeReqeustDto dto);

    public ServiceLikeResponseDto cancleLike(ServiceLikeReqeustDto dto);

    public ServiceGetBoardListResponseDto getBoardList(ServiceGetBoardRequestDto dto);

    public ServiceGetBoardResponseDto getBoardDetails(ServiceGetBoardRequestDto dto);
}
