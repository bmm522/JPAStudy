package com.callbus.community.repository;

import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("글 작성")
    public void insertBoardTest(){
        String title = "글 작성 제목 테스트";
        String content = "글 작성 내용 테스트";
        Member member = Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.Realtor)
                .status(Status.Y)
                .build();
        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();

        board.addMember(member);
        memberRepository.save(member);

        Board checkData = boardRepository.save(board);

        assertThat(checkData.getBoardId()).isEqualTo(4L);
        assertThat(checkData.getTitle()).isEqualTo("글 작성 제목 테스트");
        assertThat(checkData.getContent()).isEqualTo("글 작성 내용 테스트");
        assertThat(checkData.getHit()).isEqualTo(0);
        assertThat(checkData.getMember().getNickname()).isEqualTo("김지인");
        assertThat(checkData.getMember().getAccountType().getAccountType()).isEqualTo("Realtor");
        assertThat(checkData.getMember().getAccountId()).isEqualTo("Realtor 1");
    }




}
