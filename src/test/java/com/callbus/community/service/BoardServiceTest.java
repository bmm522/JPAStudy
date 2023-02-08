package com.callbus.community.service;

import com.callbus.community.service.dto.request.ServiceSaveBoardRequestDto;
import com.callbus.community.service.dto.request.ServiceUpdateBoardReqeustDto;
import com.callbus.community.service.dto.response.ServiceSaveBoardResponseDto;
import com.callbus.community.service.dto.response.ServiceUpdateBoardResponseDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.Status;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.callbus.community.service.Impl.BoardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

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

    @Test
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

    }

    @Test
    @Sql("classpath:schema.sql")
    @Sql("classpath:data.sql")
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

}
