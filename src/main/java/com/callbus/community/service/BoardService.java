package com.callbus.community.service;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.request.MemberReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;

public interface BoardService {

    public BoardSaveRespDto saveBoard(BoardSaveReqDto dto, MemberReqDto memberReqDto);

    public BoardSaveRespDto updateBoard(Long boardId, BoardSaveReqDto boardSaveReqDto);
}
