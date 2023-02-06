package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.request.MemberReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.controller.dto.response.CommonRespDto;
import com.callbus.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    // 글 저장
    @PostMapping("/api/v1/community/board")
    public ResponseEntity<?> saveBoard(@RequestBody @Valid BoardSaveReqDto boardSaveReqDto,BindingResult bindingResult ,@RequestAttribute("memberReqDto") MemberReqDto memberReqDto){

        if(memberReqDto.getAccountType().equals("externalUser")){
            throw new RuntimeException("글 권한이 없는 회원입니다.");
        }

        BoardSaveRespDto boardSaveRespDto = boardService.saveBoard(boardSaveReqDto, memberReqDto);
        return new ResponseEntity<>(CommonRespDto.builder().code(1).msg("글 저장 성공").body(boardSaveRespDto).build(), HttpStatus.CREATED);
    }

}
