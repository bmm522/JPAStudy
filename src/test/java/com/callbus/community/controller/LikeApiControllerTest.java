package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientLikeSaveRequestDto;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.Status;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.LikeRepository;
import com.callbus.community.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikeApiControllerTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TestRestTemplate rt;

    private final HttpHeaders headers = StaticStore.getHeaders();
    private final ObjectMapper objectMapper = StaticStore.getObjectMapper();



    @Test
    @DisplayName("정상적인 글 목록에서의 좋아요 저장 테스트")
    public void saveLikeWhenBoardsTest() throws JsonProcessingException {
        ClientLikeSaveRequestDto clientLikeSaveRequestDto = ClientLikeSaveRequestDto.builder()
                .boardId(1L)
                .build();
        headers.set("Authentication", " Realtor 1");
        String body = objectMapper.writeValueAsString(clientLikeSaveRequestDto);

        ResponseEntity<String> response = exchangeWhenBoards(headers, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        int likeCount = dc.read("$.body.likeCount");


        assertThat(code).isEqualTo(1);
        assertThat(msg).isEqualTo("좋아요 성공");
        assertThat(likeCount).isEqualTo(1);
    }

    @Test
    @DisplayName("정상적인 글 한건 보기 에서의 좋아요 저장 테스트")
    public void saveLikeWhenBoardDetailsTest() throws JsonProcessingException {
        Long boardId = 2L;
        headers.set("Authentication", " Realtor 2");

        ResponseEntity<String> response = exchangeWhenBoardDetails(headers, boardId);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        int likeCount = dc.read("$.body.likeCount");


        assertThat(code).isEqualTo(1);
        assertThat(msg).isEqualTo("좋아요 성공");
        assertThat(likeCount).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 좋아요 한 글 목록 에서의 좋아요 저장 테스트")
    public void saveLikeWhenBoardsIfDuplicatesTest() throws JsonProcessingException {
        ClientLikeSaveRequestDto clientLikeSaveRequestDto = ClientLikeSaveRequestDto.builder()
                .boardId(3L)
                .build();
        headers.set("Authentication", " Realtor 3");
        String body = objectMapper.writeValueAsString(clientLikeSaveRequestDto);

        exchangeWhenBoards(headers, body);
        ResponseEntity<String> response = exchangeWhenBoards(headers, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");

        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("이미 좋아요를 누른 글 입니다.");

    }

    @Test
    @DisplayName("이미 좋아요 한 글 한건 에서의 좋아요 저장 테스트")
    public void saveDuplicateLikeWhenBoardsIfDuplicateTest() throws JsonProcessingException {
        Long boardId = 3L;
        headers.set("Authentication", " Realtor 3");
        exchangeWhenBoardDetails(headers,boardId);
        ResponseEntity<String> response = exchangeWhenBoardDetails(headers, boardId);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");

        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("이미 좋아요를 누른 글 입니다.");

    }

    @Test
    @DisplayName("외부 사용자 좋아요 저장 테스트")
    public void saveLikeWhenExternalUserTest() throws JsonProcessingException {
        Long boardId = 3L;
        headers.remove("Authentication");
        exchangeWhenBoardDetails(headers,boardId);
        ResponseEntity<String> response = exchangeWhenBoardDetails(headers, boardId);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");

        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("권한이 없는 회원입니다.");
    }

    private ResponseEntity<String> exchangeWhenBoards(HttpHeaders headers, String body){
        return rt.exchange(
                "/api/v1/community/boards/like",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class
        );
    }

    private ResponseEntity<String> exchangeWhenBoardDetails(HttpHeaders headers, Long boardId){
        return rt.exchange(
                "/api/v1/community/board/like/"+boardId,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
    }
}
