package com.callbus.community.service;

import com.callbus.community.domain.Board;
import com.callbus.community.service.dto.request.*;
import com.callbus.community.service.dto.response.*;

public interface BoardService {



    public ServiceSaveBoardResponseDto saveBoard(ServiceSaveBoardRequestDto serviceSaveBoardRequestDto);

    public ServiceUpdateBoardResponseDto updateBoard(ServiceUpdateBoardReqeustDto serviceUpdateBoardReqeustDto);

    public ServiceDeleteBoardResponseDto deleteBoard(ServiceDeleteBoardRequestDto serviceDeleteBoardRequestDto);

    public ServiceLikeResponseDto saveLike(ServiceLikeReqeustDto serviceLikeReqeustDto);

    public ServiceLikeResponseDto cancleLike(ServiceLikeReqeustDto serviceLikeReqeustDto);

    public ServiceGetBoardListResponseDto getBoardList(ServiceGetBoardRequestDto serviceGetBoardRequestDto);

    public ServiceGetBoardResponseDto getBoardDetails(ServiceGetBoardRequestDto serviceGetBoardRequestDto);
}
