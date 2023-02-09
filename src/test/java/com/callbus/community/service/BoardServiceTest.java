package com.callbus.community.service;

import com.callbus.community.domain.Like;
import com.callbus.community.service.dto.request.ServiceGetBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceSaveBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceUpdateBoardReqeustDto;
import com.callbus.community.service.dto.response.ServiceDeleteBoardResponseDto;
import com.callbus.community.service.dto.response.ServiceGetBoardListResponseDto;
import com.callbus.community.service.dto.response.ServiceSaveBoardResponseDto;
import com.callbus.community.service.dto.response.ServiceUpdateBoardResponseDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.Status;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.callbus.community.service.Impl.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardServiceImpl boardService;


    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberRepository memberRepository;



    @BeforeEach
    public void insertData(){
        Member member =  Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.Realtor)
                .status(Status.Y)
                .build();
        Board board = Board.builder()
                .boardId(1L)
                .title("첫번째 글 제목")
                .content("첫번째 글 내용")
                .status(Status.Y)
                .build();

        Board board2 = Board.builder()
                .boardId(1L)
                .title("두번째 글 제목")
                .content("두번째 글 내용")
                .status(Status.Y)
                .build();

        board.addMember(member);
        board2.addMember(member);

        boardRepository.save(board);
        boardRepository.save(board2);

    }

    @Test
    @Sql("classpath:db/tableInit.sql")
    @DisplayName("서비스단 글 작성 테스트")
    public void saveBoardTest(){
        Optional<Member> member =  Optional.of(Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.Realtor)
                .status(Status.Y)
                .build());

        ServiceSaveBoardRequestDto serviceSaveBoardRequestDto = ServiceSaveBoardRequestDto.builder()
                .title("글 작성 서비스 단 테스트 제목")
                .content("글 작성 서비스 단 테스트 내용")
                .memberId(1L)
                .accountType("Realtor")
                .build();

        Board board = serviceSaveBoardRequestDto.toEntity();
        board.addMember(member.get());


        when(memberRepository.findByMemberId(any())).thenReturn(member);
        when(boardRepository.save(any())).thenReturn(board);

        ServiceSaveBoardResponseDto serviceSaveBoardResponseDto = boardService.saveBoard(serviceSaveBoardRequestDto);


        assertThat(serviceSaveBoardResponseDto.getTitle()).isEqualTo("글 작성 서비스 단 테스트 제목");
        assertThat(serviceSaveBoardResponseDto.getNickname()).isEqualTo("김지인");
        assertThat(board.getStatus()).isEqualTo(Status.Y);
    }

    @Test
    @Sql("classpath:db/tableInit.sql")
    @DisplayName("서비스단 글 수정 테스트")
    public void updateBoardTest(){

        ServiceUpdateBoardReqeustDto serviceUpdateBoardReqeustDto = ServiceUpdateBoardReqeustDto.builder()
                .boardId(1L)
                .title("글 수정 서비스 단 변경 후 제목")
                .content("글 수정 서비스 단 변경 후 내용")
                .build();


        Optional<Member> member =  Optional.of(Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.Realtor)
                .status(Status.Y)
                .build());

        Board board = Board.builder()
                .boardId(1L)
                .title("글 수정 서비스 단 변경 전 제목")
                .content("글 수정 서비스 단 변경 전 내용")
                .build();

        board.addMember(member.get());

        when(boardRepository.findByBoardId(serviceUpdateBoardReqeustDto.getBoardId())).thenReturn(Optional.of(board));

       ServiceUpdateBoardResponseDto serviceUpdateBoardResponseDto = boardService.updateBoard(serviceUpdateBoardReqeustDto);

        assertThat(serviceUpdateBoardResponseDto.getBoardId()).isEqualTo(1);
        assertThat(serviceUpdateBoardResponseDto.getTitle()).isEqualTo("글 수정 서비스 단 변경 후 제목");

    }

    @Test
    @Sql("classpath:db/tableInit.sql")
    @DisplayName("서비스단 글 삭제 테스트")
    public void deleteBoardTest(){
        Long boardId = 1L;
        Board board = getBoardListForTest().get(0);

        when(boardRepository.findByBoardId(boardId)).thenReturn(Optional.of(board));

        ServiceDeleteBoardResponseDto serviceDeleteBoardResponseDto = boardService.deleteBoard(boardId);

        assertThat(serviceDeleteBoardResponseDto.getBoardId()).isEqualTo(1);
        assertThat(board.getDeleteDate()).isNotNull();
        assertThat(board.getStatus()).isEqualTo(Status.N);

    }



    @Test
    @DisplayName("글 목록 보기 테스트")
    public void getBoardListTest(){
        List<Board> boards = getBoardListForTest();

        ServiceGetBoardRequestDto serviceGetBoardRequestDto = ServiceGetBoardRequestDto.builder()
                .memberId(1L)
                .accountType(AccountType.Realtor)
                .build();

        when(boardRepository.findByStatus(Status.Y)).thenReturn(boards);

        ServiceGetBoardListResponseDto serviceGetBoardListResponseDto = boardService.getBoardList(serviceGetBoardRequestDto);

        assertThat(serviceGetBoardListResponseDto.getItems().size()).isEqualTo(2);

    }



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
                .boardId(1L)
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
