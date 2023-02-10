package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientSaveBoardRequestDto;
import com.callbus.community.controller.dto.request.ClientUpdateBoardRequestDto;
import com.callbus.community.controller.dto.request.ClientLikeSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.controller.dto.response.Code;
import com.callbus.community.service.dto.request.*;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.BoardService;
import com.callbus.community.service.dto.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "Community Api")
public class BoardApiController {

    private final BoardService boardService;

    // 글 저장
    @ApiOperation(value="게시글 저장 ", notes = "게시글을 저장합니다.", response = ClientCommonResponseDto.class)
    @PostMapping("/api/v1/community/board")
    public ResponseEntity<?> saveBoard(@RequestBody @Valid ClientSaveBoardRequestDto clientSaveBoardRequestDto, BindingResult bindingResult , @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceSaveBoardRequestDto serviceSaveBoardRequestDto = new ServiceSaveBoardRequestDto(clientSaveBoardRequestDto, clientMemberRequestDto);
        ServiceSaveBoardResponseDto serviceSaveBoardResponseDto = boardService.saveBoard(serviceSaveBoardRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 저장 성공").body(serviceSaveBoardResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 목록보기
    @ApiOperation(value="게시글 목록 보기", notes = "게시글의 목록을 봅니다.", response = ClientCommonResponseDto.class)
    @GetMapping("/api/v1/community/boards")
    public ResponseEntity<?> getBoards(@RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        ServiceGetBoardRequestDto serviceGetBoardRequestDto = new ServiceGetBoardRequestDto(clientMemberRequestDto);
        ServiceGetBoardListResponseDto serviceGetBoardListResponseDto = boardService.getBoardList(serviceGetBoardRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 목록 보기 성공").body(serviceGetBoardListResponseDto).build(), HttpStatus.OK);
    }

    // 글 한건 보기
    @ApiOperation(value="게시글 한 건 보기 ", notes = "특정 게시글 한개를 봅니다.", response = ClientCommonResponseDto.class)
    @GetMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable Long boardId,@RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        ServiceGetBoardRequestDto serviceGetBoardRequestDto = new ServiceGetBoardRequestDto(boardId,clientMemberRequestDto);
        ServiceGetBoardResponseDto serviceGetBoardResponseDto = boardService.getBoardDetails(serviceGetBoardRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 한건 보기 성공").body(serviceGetBoardResponseDto).build(), HttpStatus.OK);
    }

     // 글 목록에서의 좋아요
     @ApiOperation(value="글 목록에서의 좋아요 요청", notes = "좋아요 요청을 합니다. 이미 좋아요 한 게시글은 좋아요를 할 수 없습니다.", response = ClientCommonResponseDto.class)
    @PostMapping("/api/v1/community/boards/like")
    public ResponseEntity<?> saveLikeOnBoards(@RequestBody ClientLikeSaveRequestDto clientLikeSaveRequestDto, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(clientLikeSaveRequestDto, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto =  boardService.saveLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 성공").body(serviceLikeResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 상세 보기에서의 좋아요
    @ApiOperation(value="글 한건 보기에서의 좋아요 요청 ", notes = "좋아요 요청을 합니다. 이미 좋아요 한 게시글은 좋아요를 할 수 없습니다.", response = ClientCommonResponseDto.class)
    @PostMapping("/api/v1/community/board/like/{boardId}")
    public ResponseEntity<?> saveLikeOnBoard(@PathVariable Long boardId, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(boardId, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto =  boardService.saveLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 성공").body(serviceLikeResponseDto).build(), HttpStatus.CREATED);
    }

    // 글 목록에서의 좋아요 취소
    @ApiOperation(value="글 목록에서의 좋아요 취소 ", notes = "좋아요를 취소 합니다.", response = ClientCommonResponseDto.class)
    @DeleteMapping("/api/v1/community/boards/like")
    public ResponseEntity<?> cancleLikeOnBoards(@RequestBody ClientLikeSaveRequestDto clientLikeSaveRequestDto, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(clientLikeSaveRequestDto, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto = boardService.cancleLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 취소 성공").body(serviceLikeResponseDto).build(),HttpStatus.OK);

    }

    // 글 상세 보기에서의 좋아요 취소
    @ApiOperation(value="글 한건 보기에서의 좋아요 취소 ", notes = "좋아요를 취소 합니다.", response = ClientCommonResponseDto.class)
    @DeleteMapping("/api/v1/community/board/like/{boardId}")
    public ResponseEntity<?> cancleLikeOnBoards(@PathVariable Long boardId, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        checkMemberAuthority(clientMemberRequestDto);
        ServiceLikeReqeustDto serviceLikeReqeustDto = new ServiceLikeReqeustDto(boardId, clientMemberRequestDto);
        ServiceLikeResponseDto serviceLikeResponseDto = boardService.cancleLike(serviceLikeReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("좋아요 취소 성공").body(serviceLikeResponseDto).build(),HttpStatus.OK);

    }

    // 글 수정
    @ApiOperation(value="기존 글 수정", notes = "기존 글을 수정합니다. 본인의 글이 아니면 수정이 불가능합니다.", response = ClientCommonResponseDto.class)
    @PatchMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody @Valid ClientUpdateBoardRequestDto clientUpdateBoardRequestDto, BindingResult bindingResult, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        ServiceUpdateBoardReqeustDto serviceUpdateBoardReqeustDto = new ServiceUpdateBoardReqeustDto(boardId, clientUpdateBoardRequestDto, clientMemberRequestDto);
        ServiceUpdateBoardResponseDto serviceUpdateBoardResponseDto = boardService.updateBoard(serviceUpdateBoardReqeustDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 수정 성공").body(serviceUpdateBoardResponseDto).build(),HttpStatus.OK);
    }

    // 글 삭제
    @ApiOperation(value="기존 글 삭제", notes = "기존 글을 삭제합니다. 본인의 글이 아니면 삭제가 불가능합니다.", response = ClientCommonResponseDto.class)
    @DeleteMapping("/api/v1/community/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @RequestAttribute("memberReqDto") ClientMemberRequestDto clientMemberRequestDto){
        ServiceDeleteBoardRequestDto serviceDeleteBoardRequestDto = new ServiceDeleteBoardRequestDto(boardId, clientMemberRequestDto);
        ServiceDeleteBoardResponseDto serviceDeleteBoardResponseDto = boardService.deleteBoard(serviceDeleteBoardRequestDto);
        return new ResponseEntity<>(ClientCommonResponseDto.builder().code(Code.SUCCESS.getCode()).msg("글 삭제 성공").body(serviceDeleteBoardResponseDto).build(),HttpStatus.OK);
    }

    private void checkMemberAuthority(ClientMemberRequestDto clientMemberRequestDto){
        if(clientMemberRequestDto.getAccountType().equals("externalUser")){
            throw new RuntimeException("권한이 없는 회원입니다.");
        }
    }
}
