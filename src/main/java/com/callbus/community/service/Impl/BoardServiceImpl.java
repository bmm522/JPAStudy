package com.callbus.community.service.Impl;

import com.callbus.community.domain.Like;
import com.callbus.community.repository.LikeRepository;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.request.ServiceBoardUpdateReqeustDto;
import com.callbus.community.service.dto.request.ServiceLikeSaveReqeustDto;
import com.callbus.community.service.dto.response.ServiceBoardDeleteResponseDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.Status;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.callbus.community.service.BoardService;
import com.callbus.community.service.dto.response.ServiceBoardUpdateResponseDto;
import com.callbus.community.service.dto.response.ServiceLikeResponseDto;
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

    private final LikeRepository likeRepository;



    // 글 저장
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceBoardSaveResponseDto saveBoard(ServiceBoardSaveRequestDto dto){
        Board board =dto.toEntity();
        board.addMember(getOptionalMember(dto.getMemberId()).get());
        return boardRepository.save(board).toSaveDto();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceBoardUpdateResponseDto updateBoard(ServiceBoardUpdateReqeustDto dto) {
        Board beforeModificationBoard = getOptionalBoard(dto.getBoardId()).get();
        Board AfterModificationBoard = beforeModificationBoard.update(dto.getTitle(), dto.getContent(), LocalDateTime.now());
        return AfterModificationBoard.toUpdateDto();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceBoardDeleteResponseDto deleteBoard(Long boardId) {
        Board board = getOptionalBoard(boardId).get();
        return board.delete(LocalDateTime.now(), Status.N).toDeleteDto();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceLikeResponseDto saveLike(ServiceLikeSaveReqeustDto dto) {
        if(!likeRepository.findByBoardIdAndMemberId(dto.getBoardId(), dto.getMemberId()).isPresent()){
            Like like =dto.toEntity();
            Board board = getOptionalBoard(dto.getBoardId()).get();
            like.addBoard(board);
            like.addMember(getOptionalMember(dto.getMemberId()).get());
            return likeRepository.save(like).toDto(board);
        } else {
            throw new RuntimeException("이미 좋아요를 누른 글 입니다.");
        }

    }

    private Optional<Board> getOptionalBoard(Long boardId) {
        return  Optional.of(boardRepository.findByBoardId(boardId)
                .orElseThrow(()->new RuntimeException("해당 게시글의 정보를 찾을 수 없습니다.")));
    }

    private Optional<Member> getOptionalMember(Long memberId){
        return Optional.of(memberRepository.findByMemberId(memberId)
                .orElseThrow(()->new RuntimeException("해당 회원의 정보를 찾을 수 없습니다.")));
    }


}
