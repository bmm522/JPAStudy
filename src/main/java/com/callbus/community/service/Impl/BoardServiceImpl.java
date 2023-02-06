package com.callbus.community.service.Impl;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.callbus.community.service.BoardService;
import com.callbus.community.common.HeaderSeparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;



    // 글 저장
    @Transactional(rollbackFor = RuntimeException.class)
    public BoardSaveRespDto saveBoard(BoardSaveReqDto dto, String authentication){

        Board board = dto.toEntity();

        Long memberId = HeaderSeparator.getInstance().getIdFromAuthentication(authentication);

        Optional<Member> member = Optional.of(memberRepository.findByMemberId(memberId)
                .orElseThrow(()->new RuntimeException("해당 회원의 정보를 찾을 수 없습니다.")));

        board.addMember(member.get());


        return boardRepository.save(board).toSaveDto();

    }
}
