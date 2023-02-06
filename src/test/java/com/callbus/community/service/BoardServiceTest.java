package com.callbus.community.service;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.STATUS;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.callbus.community.service.Impl.BoardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    @DisplayName("서비스단 : 글 작성 테스트")
    public void saveBoardTest(){
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("글 작성 서비스 단 테스트 제목")
                .content("글 작성 서비스 단 테스트 내용")
                .build();
        String authentication = "Realtor 1";
        Optional<Member> member =  Optional.of(Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.REALTOR)
                .status(STATUS.Y)
                .build());
        Board board = boardSaveReqDto.toEntity();
        board.addMember(member.get());


        when(memberRepository.findByMemberId(any())).thenReturn(member);
        when(boardRepository.save(any())).thenReturn(board);

        BoardSaveRespDto boardSaveRespDto = boardService.saveBoard(boardSaveReqDto, authentication);

        assertThat(boardSaveRespDto.getTitle()).isEqualTo("글 작성 서비스 단 테스트 제목");
        assertThat(boardSaveRespDto.getContent()).isEqualTo("글 작성 서비스 단 테스트 내용");
        assertThat(boardSaveRespDto.getNickname()).isEqualTo("김지인");
        assertThat(boardSaveRespDto.getMemberId()).isEqualTo(1);
    }
}
