package com.personal.community.service;

import com.personal.community.domain.Like;
import com.personal.community.repository.LikeRepository;
import com.callbus.community.service.dto.request.*;
import com.callbus.community.service.dto.response.*;
import com.personal.community.domain.Board;
import com.personal.community.domain.Member;
import com.personal.community.domain.util.AccountType;
import com.personal.community.domain.util.Status;
import com.personal.community.repository.BoardRepository;
import com.personal.community.repository.MemberRepository;
import com.personal.community.service.Impl.BoardServiceImpl;
import com.personal.community.service.dto.request.*;
import com.personal.community.service.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;


    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LikeRepository likeRepository;

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName("서비스단 글 작성 테스트")
    public void saveBoardTest(){
        Member member = getBoardListForTest().get(0).getMember();
        ServiceSaveBoardRequestDto serviceSaveBoardRequestDto = ServiceSaveBoardRequestDto.builder()
                .title("글 작성 서비스 단 테스트 제목")
                .content("글 작성 서비스 단 테스트 내용")
                .memberId(1L)
                .accountType("Realtor")
                .build();
        Board board = serviceSaveBoardRequestDto.toEntity();
        board.addMember(member);


        when(memberRepository.findByMemberId(any())).thenReturn(Optional.of(member));
        when(boardRepository.save(any())).thenReturn(board);

        ServiceSaveBoardResponseDto serviceSaveBoardResponseDto = boardService.saveBoard(serviceSaveBoardRequestDto);


        assertThat(serviceSaveBoardResponseDto.getTitle()).isEqualTo("글 작성 서비스 단 테스트 제목");
        assertThat(serviceSaveBoardResponseDto.getNickname()).isEqualTo("김지인");
        assertThat(board.getStatus()).isEqualTo(Status.Y);
    }

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName("글 목록 보기 테스트")
    public void getBoardListTest(){
        List<Board> boards = getBoardListForTest();

        ServiceGetBoardRequestDto serviceGetBoardRequestDto = ServiceGetBoardRequestDto.builder()
                .memberId(1L)
                .accountType(AccountType.Realtor)
                .build();

        when(boardRepository.findByStatus(Status.Y)).thenReturn(boards);

        ServiceGetBoardListResponseDto serviceGetBoardListResponseDto = boardService.getBoardList(serviceGetBoardRequestDto);
        List<ServiceGetBoardResponseDto> serviceGetBoardResponseDtos = serviceGetBoardListResponseDto.getItems();

        assertThat(serviceGetBoardListResponseDto.getItems().size()).isEqualTo(2);
        assertThat(serviceGetBoardResponseDtos.get(0).getContent()).isEqualTo("첫번째 글 내용");
        assertThat(serviceGetBoardResponseDtos.get(0).getTargetMemberIsLike()).isEqualTo("Y");
        assertThat(serviceGetBoardResponseDtos.get(1).getTargetMemberIsLike()).isEqualTo("N");
        assertThat(serviceGetBoardResponseDtos.get(0).getTargetMemberModificationPermission()).isEqualTo("Y");
        assertThat(serviceGetBoardResponseDtos.get(1).getTargetMemberModificationPermission()).isEqualTo("N");
    }

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName("글 한건 보기 테스트")
    public void getBoardDetailsTest(){
        List<Board> boards = getBoardListForTest();

        ServiceGetBoardRequestDto serviceGetBoardRequestDto = ServiceGetBoardRequestDto.builder()
                .memberId(1L)
                .boardId(1L)
                .accountType(AccountType.Realtor)
                .build();

        when(boardRepository.findByBoardId(serviceGetBoardRequestDto.getBoardId())).thenReturn(Optional.of(boards.get(0)));

        ServiceGetBoardResponseDto serviceGetBoardResponseDto = boardService.getBoardDetails(serviceGetBoardRequestDto);

        assertThat(serviceGetBoardResponseDto.getContent()).isEqualTo("첫번째 글 내용");
        assertThat(serviceGetBoardResponseDto.getTargetMemberIsLike()).isEqualTo("Y");
        assertThat(serviceGetBoardResponseDto.getTargetMemberModificationPermission()).isEqualTo("Y");
        assertThat(serviceGetBoardResponseDto.getHit()).isEqualTo(1);
    }

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName("서비스단 글 수정 테스트")
    public void updateBoardTest(){
        Board board = getBoardListForTest().get(0);
        ServiceUpdateBoardReqeustDto serviceUpdateBoardReqeustDto = ServiceUpdateBoardReqeustDto.builder()
                .boardId(1L)
                .title("글 수정 서비스 단 변경 후 제목")
                .content("글 수정 서비스 단 변경 후 내용")
                .memberId(1L)
                .build();


        when(boardRepository.findByBoardId(serviceUpdateBoardReqeustDto.getBoardId())).thenReturn(Optional.of(board));

       ServiceUpdateBoardResponseDto serviceUpdateBoardResponseDto = boardService.updateBoard(serviceUpdateBoardReqeustDto);

        assertThat(serviceUpdateBoardResponseDto.getBoardId()).isEqualTo(1);
        assertThat(serviceUpdateBoardResponseDto.getTitle()).isEqualTo("글 수정 서비스 단 변경 후 제목");

    }

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName("서비스단 글 삭제 테스트")
    public void deleteBoardTest(){
        ServiceDeleteBoardRequestDto serviceDeleteBoardRequestDto = ServiceDeleteBoardRequestDto.builder()
                .boardId(1L)
                .memberId(1L)
                .build();
        Board board = getBoardListForTest().get(0);

        when(boardRepository.findByBoardId(serviceDeleteBoardRequestDto.getBoardId())).thenReturn(Optional.of(board));

        ServiceDeleteBoardResponseDto serviceDeleteBoardResponseDto = boardService.deleteBoard(serviceDeleteBoardRequestDto);

        assertThat(serviceDeleteBoardResponseDto.getBoardId()).isEqualTo(1);
        assertThat(board.getDeleteDate()).isNotNull();
        assertThat(board.getStatus()).isEqualTo(Status.N);
    }

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName(" 서비스단 글 좋아요 등록 테스트")
    public void saveLikeTest(){
        ServiceLikeReqeustDto serviceLikeReqeustDto = ServiceLikeReqeustDto.builder()
                .boardId(2L)
                .memberId(1L)
                .build();

        Board board = getBoardListForTest().get(1);
        Like like = board.getLikes().get(0);

        when(boardRepository.findByBoardId(any())).thenReturn(Optional.of(board));
        when(memberRepository.findByMemberId(any())).thenReturn(Optional.of(board.getMember()));
        when(likeRepository.findByBoardIdAndMemberId(any(),any())).thenReturn(Optional.empty());
        when(likeRepository.save(any())).thenReturn(like);

        ServiceLikeResponseDto serviceLikeResponseDto = boardService.saveLike(serviceLikeReqeustDto);

        assertThat(serviceLikeResponseDto.getBoardId()).isEqualTo(2);
        assertThat(serviceLikeResponseDto.getLikeCount()).isEqualTo(1);
    }

    @Test
    @Sql("classpath:dbForTest/tableInit.sql")
    @DisplayName("서비스단 글 좋아요 취소 테스트")
    public void cancleLikeTest(){
        ServiceLikeReqeustDto serviceLikeReqeustDto = ServiceLikeReqeustDto.builder()
                .boardId(2L)
                .memberId(3L)
                .build();
        Board board = getBoardListForTest().get(1);
        Member member = board.getMember();
        Like like = board.getLikes().get(0);

        lenient().when(boardRepository.findByBoardId(any())).thenReturn(Optional.of(board));
        lenient().when(memberRepository.findByMemberId(any())).thenReturn(Optional.of(member));
        lenient().when(likeRepository.findByBoardIdAndMemberId(any(), any())).thenReturn(Optional.of(like));
        ServiceLikeResponseDto serviceLikeResponseDtoTest = like.toDto(board);
        like.addBoard(board);

        ServiceLikeResponseDto serviceLikeResponseDto = boardService.cancleLike(serviceLikeReqeustDto);

        assertThat(serviceLikeResponseDto.getBoardId()).isEqualTo(2);
        assertThat(serviceLikeResponseDto.getLikeCount()-1).isEqualTo(0);
    }



    // 더미 객체 세팅
    private List<Board> getBoardListForTest(){
        List<Board> boards = new ArrayList<>();

        Member member =  Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.Realtor)
                .status(Status.Y)
                .build();
        Member member2 =  Member.builder()
                .id(2L)
                .nickname("강건강")
                .accountType(AccountType.Lessee)
                .status(Status.Y)
                .build();
        Member member3 =  Member.builder()
                .id(3L)
                .nickname("남나눔")
                .accountType(AccountType.Lessor)
                .status(Status.Y)
                .build();

        Like like = Like.builder()
                .likeId(1L)
                .likeCreateDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Like like2 =Like.builder()
                .likeId(2L)
                .likeCreateDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Like like3 = Like.builder()
                .likeId(3L)
                .likeCreateDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        member.addLike(like);
        member2.addLike(like2);
        member3.addLike(like3);

        List<Like> likes = new ArrayList<>();
        List<Like> likes2 = new ArrayList<>();

        likes.add(like);
        likes.add(like2);
        likes2.add(like3);

        Board board = Board.builder()
                .boardId(1L)
                .title("첫번째 글 제목")
                .content("첫번째 글 내용")
                .status(Status.Y)
                .createDate(LocalDateTime.now())
                .likes(likes)
                .build();

        Board board2 = Board.builder()
                .boardId(2L)
                .title("두번째 글 제목")
                .content("두번째 글 내용")
                .status(Status.Y)
                .createDate(LocalDateTime.now())
                .likes(likes2)
                .build();

        member.addBoard(board);
        member2.addBoard(board2);

        boards.add(board);
        boards.add(board2);

        return boards;
    }


}
