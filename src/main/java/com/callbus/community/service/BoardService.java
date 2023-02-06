package com.callbus.community.service;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;

public interface BoardService {

    public BoardSaveRespDto saveBoard(BoardSaveReqDto dto, String authentication);
}
