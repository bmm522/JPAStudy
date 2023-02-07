package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardUpdateReqeustDto;
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
        ServiceBoardUpdateReqeustDto serviceBoardUpdateReqeustDto = new ServiceBoardUpdateReqeustDto(boardId, clientBoardUpdateRequestDto);
        ClientCommonResponseDto<?> clientCommonResponseDto = boardService.updateBoard(serviceBoardUpdateReqeustDto);
        return new ResponseEntity<>(clientCommonResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
        ClientCommonResponseDto<?> clientCommonResponseDto = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(clientCommonResponseDto,HttpStatus.OK);
    }

}
