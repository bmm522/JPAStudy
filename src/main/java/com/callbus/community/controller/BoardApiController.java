package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.BoardSaveRequestDto;
import com.callbus.community.controller.dto.request.BoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.MemberRequestDto;
import com.callbus.community.controller.dto.response.BoardSaveResponseDto;
import com.callbus.community.controller.dto.response.BoardUpdateResponseDto;
import com.callbus.community.controller.dto.response.CommonResponseDto;
import com.callbus.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 글 저장
    @PostMapping("/api/v1/community/board")
    public ResponseEntity<?> saveBoard(@RequestBody @Valid BoardSaveRequestDto boardSaveRequestDto, BindingResult bindingResult , @RequestAttribute("memberReqDto") MemberRequestDto memberReqDto){

        if(memberReqDto.getAccountType().equals("externalUser")){
            throw new RuntimeException("글 권한이 없는 회원입니다.");
        }

        BoardSaveResponseDto boardSaveResponseDto = boardService.saveBoard(boardSaveRequestDto, memberReqDto);
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 저장 성공").body(boardSaveResponseDto).build(), HttpStatus.CREATED);
    }

    @PatchMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody @Valid BoardUpdateRequestDto boardUpdateRequestDto, BindingResult bindingResult){
        BoardUpdateResponseDto boardUpdateRespDto = boardService.updateBoard(boardId, boardUpdateRequestDto);
        return new ResponseEntity<>(CommonResponseDto.builder().code(1).msg("글 수정 성공").body(boardUpdateRespDto).build(),HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/community/board/{boardId}")

}
