package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.response.BoardDeleteResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.service.dto.response.BoardUpdateResponseDto;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
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
    public ResponseEntity<?> saveBoard(@RequestBody @Valid ClientBoardSaveRequestDto clientBoardSaveRequestDto, BindingResult bindingResult , @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){

        if(clientMemberRequestDto.getAccountType().equals("externalUser")){
            throw new RuntimeException("글 권한이 없는 회원입니다.");
        }

        ServiceBoardSaveRequestDto serviceBoardSaveRequestDto = new ServiceBoardSaveRequestDto(clientBoardSaveRequestDto, clientMemberRequestDto);

        ClientCommonResponseDto<?> clientCommonResponseDto = boardService.saveBoard(serviceBoardSaveRequestDto);

        return new ResponseEntity<>(clientCommonResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody @Valid ClientBoardUpdateRequestDto clientBoardUpdateRequestDto, BindingResult bindingResult){
        BoardUpdateResponseDto boardUpdateRespDto = boardService.updateBoard(boardId, clientBoardUpdateRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(1).msg("글 수정 성공").body(boardUpdateRespDto).build(),HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
        BoardDeleteResponseDto boardDeleteResponseDto = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(1).msg("글 삭제 성공").body(boardDeleteResponseDto).build(),HttpStatus.OK);

    }

}
