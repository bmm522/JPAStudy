package com.callbus.community.service.Impl;

import com.callbus.community.domain.Like;
import com.callbus.community.repository.LikeRepository;
import com.callbus.community.service.dto.request.ServiceGetBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceSaveBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceUpdateBoardReqeustDto;
import com.callbus.community.service.dto.request.ServiceLikeReqeustDto;
import com.callbus.community.service.dto.response.*;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    private final LikeRepository likeRepository;



    // 글 저장
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceSaveBoardResponseDto saveBoard(ServiceSaveBoardRequestDto dto){
        Board board =dto.toEntity();
        board.addMember(getOptionalMember(dto.getMemberId()).get());
        return boardRepository.save(board).toSaveDto();
    }

    // 글 목록 보기
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceGetBoardListResponseDto getBoardList(ServiceGetBoardRequestDto serviceGetBoardRequestDto) {
        Long targetMemberId = serviceGetBoardRequestDto.getMemberId();
        List<ServiceGetBoardResponseDto> boardDtos = boardRepository.findByStatus(Status.Y).stream()
                .map((board) -> board.toGetDto(targetMemberId))
                .collect(Collectors.toList());
        return ServiceGetBoardListResponseDto.builder().serviceGetBoardResponseDtos(boardDtos).build();
    }

    // 글 한건 보기
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceGetBoardResponseDto getBoardDetails(ServiceGetBoardRequestDto serviceGetBoardRequestDto) {
        Optional<Board> boardOp = boardRepository.findByBoardId(serviceGetBoardRequestDto.getBoardId());
        if (boardOp.isPresent()){
            Board board = boardOp.get();
            board.updateHit(board.getHit());
            return board.toGetDto(serviceGetBoardRequestDto.getMemberId());
        }
        throw new RuntimeException("없는 글 입니다.");
    }

    // 글 수정
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceUpdateBoardResponseDto updateBoard(ServiceUpdateBoardReqeustDto dto) {
        Board beforeModificationBoard = getOptionalBoard(dto.getBoardId()).get();
        Board AfterModificationBoard = beforeModificationBoard.update(dto.getTitle(), dto.getContent(), LocalDateTime.now());
        return AfterModificationBoard.toUpdateDto();
    }

    // 글 삭제
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceDeleteBoardResponseDto deleteBoard(Long boardId) {
        Board board = getOptionalBoard(boardId).get();
        return board.delete(LocalDateTime.now(), Status.N).toDeleteDto();
    }

    // 좋아요
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceLikeResponseDto saveLike(ServiceLikeReqeustDto dto) {

        if(!checkAlreadyExistLike(dto.getBoardId(), dto.getMemberId())){

            Like like =dto.toEntity();
            Board board = getOptionalBoard(dto.getBoardId()).get();
            like.addBoard(board);
            like.addMember(getOptionalMember(dto.getMemberId()).get());

            return likeRepository.save(like).toDto(board);
        }

        throw new RuntimeException("이미 좋아요를 누른 글 입니다.");


    }

    // 좋아요 취소
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceLikeResponseDto cancleLike(ServiceLikeReqeustDto dto) {
        Optional<Like> likeOp = getOptionalLike(dto.getBoardId(), dto.getMemberId());

        if(likeOp.isPresent()){
            Like like = likeOp.get();
            likeRepository.deleteByLikeId(like.getLikeId());
            return like.toDto(like.getBoard());
        }

        throw new RuntimeException("좋아요 하지 않은 글 입니다.");
    }


    private Optional<Board> getOptionalBoard(Long boardId) {
        return  Optional.of(boardRepository.findByBoardId(boardId)
                .orElseThrow(()->new RuntimeException("해당 게시글의 정보를 찾을 수 없습니다.")));
    }

    private Optional<Member> getOptionalMember(Long memberId){
        return Optional.of(memberRepository.findByMemberId(memberId)
                .orElseThrow(()->new RuntimeException("해당 회원의 정보를 찾을 수 없습니다.")));
    }


    private Optional<Like> getOptionalLike(Long boardId, Long memberId){
        return likeRepository.findByBoardIdAndMemberId(boardId, memberId);
    }

    public boolean checkAlreadyExistLike(Long boardId, Long memberId){
        return getOptionalLike(boardId, memberId).isPresent();
    }



}
