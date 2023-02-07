package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.BoardSaveReqDto;
import com.callbus.community.controller.dto.request.MemberReqDto;
import com.callbus.community.controller.dto.response.BoardSaveRespDto;
import com.callbus.community.controller.dto.response.CommonRespDto;
import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.STATUS;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardApiControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestRestTemplate rt;

    private static HttpHeaders headers;
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void readyData(){
        Member member = Member.builder()
                .id(1L)
                .nickname("김지인")
                .accountType(AccountType.REALTOR)
                .status(STATUS.Y)
                .build();
        memberRepository.save(member);
    }

    @Test
    @DisplayName("정상적인 요청 글 저장 테스트")
    public void testSaveBoardSuccess() throws JsonProcessingException {
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("글 저장 제목 테스트")
                .content("글 저장 내용 테스트")
                .build();

        headers.set("Authentication", " Realtor 1");

        String body = objectMapper.writeValueAsString(boardSaveReqDto);

        ResponseEntity<String> response = rt.postForEntity(
                "/api/v1/community/board",
                new HttpEntity<>(body, headers),
                String.class
        );

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String title =  dc.read("$.body.title");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(code).isEqualTo(1);
        assertThat(msg).isEqualTo("글 저장 성공");
        assertThat(title).isEqualTo("글 저장 제목 테스트");

    }

    @Test
    @DisplayName("헤더에 값이 없을 때 요청 글 저장 테스트")
    public void testSaveBoardWhenWithoutHeader() throws JsonProcessingException {
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("글 저장 제목 테스트")
                .content("글 저장 내용 테스트")
                .build();

        String body = objectMapper.writeValueAsString(boardSaveReqDto);
        headers.remove("Authentication");
        ResponseEntity<String> response = rt.postForEntity(
                "/api/v1/community/board",
                new HttpEntity<>(body, headers),
                String.class
        );

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String resultBody =  dc.read("$.body");


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("글 권한이 없는 회원입니다.");
        assertThat(resultBody).isEqualTo(null);
    }


    @Test
    @DisplayName("잘못된 요청 글 저장 테스트")
    public void testSaveBoardWhenInvalid() throws JsonProcessingException {
        BoardSaveReqDto boardSaveReqDto = BoardSaveReqDto.builder()
                .title("글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트" +
                        "글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트" +
                        "글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트")
                .content("글 저장 내용 테스트")
                .build();

        headers.set("Authentication", " Realtor 1");

        String body = objectMapper.writeValueAsString(boardSaveReqDto);

        ResponseEntity<String> response = rt.postForEntity(
                "/api/v1/community/board",
                new HttpEntity<>(body, headers),
                String.class
        );

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String resultBody = dc.read("$.body");


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("{title=크기가 1에서 50 사이여야 합니다}");
        assertThat(resultBody).isEqualTo(null);

    }


}
