package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.ClientLikeSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.controller.dto.response.Code;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardUpdateReqeustDto;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.BoardService;
import com.callbus.community.service.dto.request.ServiceLikeReqeustDto;
import com.callbus.community.service.dto.response.ServiceBoardDeleteResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardUpdateResponseDto;
import com.callbus.community.service.dto.response.ServiceLikeResponseDto;
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
        checkMemberAuthority(clientMemberRequestDto);
        ServiceBoardSaveRequestDto serviceBoardSaveRequestDto = new ServiceBoardSaveRequestDto(clientBoardSaveRequestDto, clientMemberRequestDto);
        ServiceBoardSaveResponseDto serviceBoardSaveResponseDto = boardService.saveBoard(serviceBoardSaveRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 저장 성공").body(serviceBoardSaveResponseDto).build(), HttpStatus.CREATED);
    }

     // 글 목록에서의 좋아요
    @PostMapping("/api/v1/community/boards/like")
    public ResponseEntity<?> saveLikeOnBoards(@RequestBody ClientLikeSaveRequestDto clientLikeSaveRequestDto, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(clientLikeSaveRequestDto, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto =  boardService.saveLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 성공").body(serviceLikeResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 한건 보기에서의 좋아요
    @PostMapping("/api/v1/community/board/like/{boardId}")
    public ResponseEntity<?> saveLikeOnBoard(@PathVariable Long boardId, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(boardId, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto =  boardService.saveLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 성공").body(serviceLikeResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 목록에서의 좋아요 취소
    @DeleteMapping("/api/v1/community/boards/like")
    public ResponseEntity<?> cancleLikeOnBoards(@RequestBody ClientLikeSaveRequestDto clientLikeSaveRequestDto, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(clientLikeSaveRequestDto, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto = boardService.cancleLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 취소 성공").body(serviceLikeResponseDto).build(),HttpStatus.OK);

    }

    // 글 수정
    @PatchMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody @Valid ClientBoardUpdateRequestDto clientBoardUpdateRequestDto, BindingResult bindingResult){
        ServiceBoardUpdateReqeustDto serviceBoardUpdateReqeustDto = new ServiceBoardUpdateReqeustDto(boardId, clientBoardUpdateRequestDto);
        ServiceBoardUpdateResponseDto serviceBoardUpdateResponseDto = boardService.updateBoard(serviceBoardUpdateReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 수정 성공").body(serviceBoardUpdateResponseDto).build(),HttpStatus.OK);
    }

    // 글 삭제
    @DeleteMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
       ServiceBoardDeleteResponseDto serviceBoardDeleteResponseDto = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 삭제 성공").body(serviceBoardDeleteResponseDto).build(),HttpStatus.OK);
    }

    private void checkMemberAuthority(ClientMemberRequestDto clientMemberRequestDto){
        if(clientMemberRequestDto.getAccountType().equals("externalUser")){
            throw new RuntimeException("권한이 없는 회원입니다.");
        }
    }
}
