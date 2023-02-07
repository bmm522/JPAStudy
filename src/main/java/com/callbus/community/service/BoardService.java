package com.callbus.community.service;

import com.callbus.community.controller.dto.request.BoardSaveRequestDto;
import com.callbus.community.controller.dto.request.BoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.MemberRequestDto;
import com.callbus.community.controller.dto.response.BoardSaveResponseDto;
import com.callbus.community.controller.dto.response.BoardUpdateResponseDto;

public interface BoardService {

    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto dto, MemberRequestDto memberReqDto);

    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto);
}
