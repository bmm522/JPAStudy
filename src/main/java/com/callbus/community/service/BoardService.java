package com.callbus.community.service;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.request.BoardUpdateReqDto;
import com.callbus.community.controller.dto.request.MemberReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.controller.dto.response.BoardUpdateRespDto;

public interface BoardService {

    public BoardSaveRespDto saveBoard(BoardSaveReqDto dto, MemberReqDto memberReqDto);

    public BoardUpdateRespDto updateBoard(Long boardId, BoardUpdateReqDto boardUpdateReqDto);
}
