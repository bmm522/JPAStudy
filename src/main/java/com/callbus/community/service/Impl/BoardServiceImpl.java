package com.callbus.community.service.Impl;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.response.BoardDeleteResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.service.dto.response.BoardUpdateResponseDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.Status;
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
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ClientCommonResponseDto<?> saveBoard(ServiceBoardSaveRequestDto serviceBoardSaveRequestDto){

        Optional<Member> member = Optional.of(memberRepository.findByMemberId(serviceBoardSaveRequestDto.getMemberId())
                .orElseThrow(()->new RuntimeException("해당 회원의 정보를 찾을 수 없습니다.")));

        Board board = serviceBoardSaveRequestDto.toEntity();
        board.addMember(member.get());

        ServiceBoardSaveResponseDto serviceBoardSaveResponseDto = boardRepository.save(board).toSaveDto();

        return ClientCommonResponseDto.builder().code(1).msg("글 저장 성공").body(serviceBoardSaveResponseDto).build();

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BoardUpdateResponseDto updateBoard(Long boardId, ClientBoardUpdateRequestDto clientBoardUpdateRequestDto) {


        Optional<Board> boardOptional = getOptionalBoard(boardId);

        Board board = boardOptional.get().update(clientBoardUpdateRequestDto.getTitle(), clientBoardUpdateRequestDto.getContent(), LocalDateTime.now());

        return board.toUpdateDto();

    }



    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BoardDeleteResponseDto deleteBoard(Long boardId) {

        Optional<Board> boardOptional = getOptionalBoard(boardId);

        Board board = boardOptional.get().delete(LocalDateTime.now(), Status.N);

        return board.toDeleteDto();
    }

    private Optional<Board> getOptionalBoard(Long boardId) {
        return  Optional.of(boardRepository.findByBoardId(boardId)
                .orElseThrow(()->new RuntimeException("해당 게시글의 정보를 찾을 수 없습니다.")));
    }


}
