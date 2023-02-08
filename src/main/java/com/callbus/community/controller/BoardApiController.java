package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientSaveBoardRequestDto;
import com.callbus.community.controller.dto.request.ClientUpdateBoardRequestDto;
import com.callbus.community.controller.dto.request.ClientLikeSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.controller.dto.response.Code;
import com.callbus.community.service.dto.request.ServiceGetBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceSaveBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceUpdateBoardReqeustDto;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.BoardService;
import com.callbus.community.service.dto.request.ServiceLikeReqeustDto;
import com.callbus.community.service.dto.response.*;
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
    public ResponseEntity<?> saveBoard(@RequestBody @Valid ClientSaveBoardRequestDto clientSaveBoardRequestDto, BindingResult bindingResult , @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceSaveBoardRequestDto serviceSaveBoardRequestDto = new ServiceSaveBoardRequestDto(clientSaveBoardRequestDto, clientMemberRequestDto);
        ServiceSaveBoardResponseDto serviceSaveBoardResponseDto = boardService.saveBoard(serviceSaveBoardRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 저장 성공").body(serviceSaveBoardResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 목록보기
    @GetMapping("/api/v1/community/boards")
    public ResponseEntity<?> getBoards(@RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        ServiceGetBoardRequestDto serviceGetBoardRequestDto = new ServiceGetBoardRequestDto(clientMemberRequestDto);
        ServiceGetBoardListResponseDto serviceGetBoardListResponseDto = boardService.getBoardList(serviceGetBoardRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 목록 보기 성공").body(serviceGetBoardListResponseDto).build(), HttpStatus.OK);
    }

     // 글 목록에서의 좋아요
    @PostMapping("/api/v1/community/boards/like")
    public ResponseEntity<?> saveLikeOnBoards(@RequestBody ClientLikeSaveRequestDto clientLikeSaveRequestDto, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(clientLikeSaveRequestDto, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto =  boardService.saveLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 성공").body(serviceLikeResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 상세 보기에서의 좋아요
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

    // 글 상세 보기에서의 좋아요 취소
    @DeleteMapping("/api/v1/community/board/like/{boardId}")
    public ResponseEntity<?> cancleLikeOnBoards(@PathVariable Long boardId, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(boardId, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto = boardService.cancleLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 취소 성공").body(serviceLikeResponseDto).build(),HttpStatus.OK);

    }

    // 글 수정
    @PatchMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody @Valid ClientUpdateBoardRequestDto clientUpdateBoardRequestDto, BindingResult bindingResult){
        ServiceUpdateBoardReqeustDto serviceUpdateBoardReqeustDto = new ServiceUpdateBoardReqeustDto(boardId, clientUpdateBoardRequestDto);
        ServiceUpdateBoardResponseDto serviceUpdateBoardResponseDto = boardService.updateBoard(serviceUpdateBoardReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 수정 성공").body(serviceUpdateBoardResponseDto).build(),HttpStatus.OK);
    }

    // 글 삭제
    @DeleteMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){
       ServiceDeleteBoardResponseDto serviceDeleteBoardResponseDto = boardService.deleteBoard(boardId);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 삭제 성공").body(serviceDeleteBoardResponseDto).build(),HttpStatus.OK);
    }

    private void checkMemberAuthority(ClientMemberRequestDto clientMemberRequestDto){
        if(clientMemberRequestDto.getAccountType().equals("externalUser")){
            throw new RuntimeException("권한이 없는 회원입니다.");
        }
    }
}
