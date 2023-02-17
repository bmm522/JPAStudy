package com.personal.community.service.Impl;

import com.personal.community.repository.entity.Like;
import com.personal.community.repository.entity.mapper.ServiceDtoToEntityMapper;
import com.personal.community.repository.entity.util.AccountType;
import com.personal.community.repository.LikeRepository;
import com.personal.community.service.dto.request.*;
import com.personal.community.repository.entity.Board;
import com.personal.community.repository.entity.Member;
import com.personal.community.repository.entity.util.Status;
import com.personal.community.repository.BoardRepository;
import com.personal.community.repository.MemberRepository;
import com.personal.community.service.BoardService;
import com.personal.community.service.dto.response.*;
import com.personal.community.service.dto.response.mapper.EntityToServiceDtoMapper;
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

    private final ServiceDtoToEntityMapper serviceDtoToEntityMapper = ServiceDtoToEntityMapper.getInstance();

    private final EntityToServiceDtoMapper entityToServiceDtoMapper = EntityToServiceDtoMapper.getInstance();

    // 글 저장
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceSaveBoardResponseDto saveBoard(ServiceSaveBoardRequestDto dto){
        Board board = serviceDtoToEntityMapper.toEntityWhenSave(dto);
        board.addMember(getMember(dto.getMemberId()));
        return entityToServiceDtoMapper.toSaveDto(boardRepository.save(board));
    }



    // 글 목록 보기
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceGetBoardListResponseDto getBoardList(ServiceGetBoardRequestDto dto) {
        Long targetMemberId = dto.getMemberId();
        AccountType accountType = dto.getAccountType();
        List<ServiceGetBoardResponseDto> boardDtos = boardRepository.findByStatus(Status.Y).stream()
                .map((board) -> board.toGetDto(targetMemberId, accountType))
                .collect(Collectors.toList());
        return ServiceGetBoardListResponseDto.builder().serviceGetBoardResponseDtos(boardDtos).build();
    }

    // 글 한건 보기
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceGetBoardResponseDto getBoardDetails(ServiceGetBoardRequestDto dto) {
        Optional<Board> boardOp = boardRepository.findByBoardId(dto.getBoardId());
        if (boardOp.isPresent()){
            Board board = boardOp.get();
            board.updateHit(board.getHit());
            return board.toGetDto(dto.getMemberId(), dto.getAccountType());
        }
        throw new RuntimeException("없는 글 입니다.");
    }

    // 글 수정
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceUpdateBoardResponseDto updateBoard(ServiceUpdateBoardReqeustDto dto) {
        Board beforeModificationBoard = getOptionalBoard(dto.getBoardId()).get();
        if(beforeModificationBoard.checkWriter(dto)) {
            Board afterModificationBoard = beforeModificationBoard.update(dto.getTitle(), dto.getContent(), LocalDateTime.now());
            return afterModificationBoard.toUpdateDto();
        }
        throw new RuntimeException("해당 글의 주인이 아닙니다.");
    }



    // 글 삭제
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceDeleteBoardResponseDto deleteBoard(ServiceDeleteBoardRequestDto dto) {
        Board board = getOptionalBoard(dto.getBoardId()).get();
        if(board.getMember().getMemberId().equals(dto.getMemberId())) {
            return board.delete(LocalDateTime.now(), Status.N).toDeleteDto();
        }
        throw new RuntimeException("해당 글의 주인이 아닙니다.");
    }

    // 좋아요
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ServiceLikeResponseDto saveLike(ServiceLikeReqeustDto dto) {

        if(!checkAlreadyExistLike(dto.getBoardId(), dto.getMemberId())){

            Like like =dto.toEntity();
            Board board = getOptionalBoard(dto.getBoardId()).get();
            like.addBoard(board);
            like.addMember(getMember(dto.getMemberId()));

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

    private Member getMember(Long memberId){
         Optional<Member> memberOptional = Optional.of(memberRepository.findByMemberId(memberId)
                .orElseThrow(()->new RuntimeException("해당 회원의 정보를 찾을 수 없습니다.")));

         return memberOptional.get();
    }


    private Optional<Like> getOptionalLike(Long boardId, Long memberId){
        return likeRepository.findByBoardIdAndMemberId(boardId, memberId);
    }

    public boolean checkAlreadyExistLike(Long boardId, Long memberId){
        return getOptionalLike(boardId, memberId).isPresent();
    }



}
