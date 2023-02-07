package com.callbus.community.service.Impl;

import com.callbus.community.controller.dto.request.BoardSaveRequestDto;
import com.callbus.community.controller.dto.request.BoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.MemberRequestDto;
import com.callbus.community.controller.dto.response.BoardSaveResponseDto;
import com.callbus.community.controller.dto.response.BoardUpdateResponseDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.callbus.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;



    // 글 저장
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto, MemberRequestDto memberReqDto){

        Board board = boardSaveRequestDto.toEntity();

        Optional<Member> member = Optional.of(memberRepository.findByMemberId(memberReqDto.getMemberId())
                .orElseThrow(()->new RuntimeException("해당 회원의 정보를 찾을 수 없습니다.")));

        board.addMember(member.get());


        return boardRepository.save(board).toSaveDto();

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {

        Optional<Board> boardOp = Optional.of(boardRepository.findByBoardId(boardId)
                .orElseThrow(()->new RuntimeException("해당 게시글의 정보를 찾을 수 없습니다.")));

        Board board = boardOp.get().update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent(), LocalDateTime.now());

        return board.toUpdateDto();

    }
}
