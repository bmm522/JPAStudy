package com.callbus.community.service;

import com.callbus.community.controller.dto.request.ClientBoardSaveRequestDto;
import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.controller.dto.request.ClientMemberRequestDto;
import com.callbus.community.controller.dto.response.ClientCommonResponseDto;
import com.callbus.community.service.dto.request.ServiceBoardSaveRequestDto;
import com.callbus.community.service.dto.response.ServiceBoardSaveResponseDto;
import com.callbus.community.service.dto.response.BoardUpdateResponseDto;
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

        ServiceBoardSaveRequestDto serviceBoardSaveRequestDto = ServiceBoardSaveRequestDto.builder()
                .title("글 작성 서비스 단 테스트 제목")
                .content("글 작성 서비스 단 테스트 내용")
                .memberId(1L)
                .accountType("Realtor")
                .build();

        Board board = serviceBoardSaveRequestDto.toEntity();
        board.addMember(member.get());


        when(memberRepository.findByMemberId(any())).thenReturn(member);
        when(boardRepository.save(any())).thenReturn(board);

        ClientCommonResponseDto<?> clientCommonResponseDto = boardService.saveBoard(serviceBoardSaveRequestDto);


        assertThat(clientCommonResponseDto.getCode()).isEqualTo(1);
        assertThat(clientCommonResponseDto.getMsg()).isEqualTo("글 저장 성공");

    }

    @Test
    @Sql("classpath:schema.sql")
    @Sql("classpath:data.sql")
    @DisplayName("서비스단 글 수정 테스트")
    public void updateBoardTest(){

        Long boardId = 1L;

        ClientBoardUpdateRequestDto clientBoardUpdateRequestDto = ClientBoardUpdateRequestDto.builder()
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

        when(boardRepository.findByBoardId(boardId)).thenReturn(Optional.of(board));

        BoardUpdateResponseDto boardUpdateRespDto = boardService.updateBoard(boardId, clientBoardUpdateRequestDto);

        assertThat(boardUpdateRespDto.getTitle()).isEqualTo("글 수정 서비스 단 변경 후 제목");
        assertThat(boardUpdateRespDto.getContent()).isEqualTo("글 수정 서비스 단 변경 후 내용");
        assertThat(boardUpdateRespDto.getNickname()).isEqualTo("김지인");
        assertThat(boardUpdateRespDto.getMemberId()).isEqualTo(1);
        assertThat(boardUpdateRespDto.getBoardId()).isEqualTo(1);
    }

}
