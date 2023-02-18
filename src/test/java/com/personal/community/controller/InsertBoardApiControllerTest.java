package com.personal.community.controller;

import com.personal.community.controller.dto.request.ClientSaveBoardRequestDto;
import com.personal.community.repository.entity.Member;
import com.personal.community.repository.entity.util.AccountType;
import com.personal.community.repository.entity.util.Status;
import com.personal.community.repository.BoardRepository;
import com.personal.community.repository.MemberRepository;
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
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsertBoardApiControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private TestRestTemplate rt;

    private final HttpHeaders headers = StaticStore.getHeaders();
    private final ObjectMapper objectMapper = StaticStore.getObjectMapper();

    @BeforeEach
    public void readyData(){
        Member member = Member.builder()
                .id(4L)
                .nickname("김지인")
                .accountType(AccountType.Realtor)
                .status(Status.Y)
                .build();
        memberRepository.save(member);
    }
    @Test
    @DisplayName("정상적인 요청 글 저장 테스트")
    public void testSaveBoardSuccess() throws JsonProcessingException {
        ClientSaveBoardRequestDto clientSaveBoardRequestDto = ClientSaveBoardRequestDto.builder()
                .title("글 저장 제목 테스트")
                .content("글 저장 내용 테스트")
                .build();

        headers.set("Authentication", " Realtor 4");
        String body = objectMapper.writeValueAsString(clientSaveBoardRequestDto);

        ResponseEntity<String> response = exchange(headers, body);

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
    @DisplayName("외부 사용자 글 저장 테스트")
    public void testSaveBoardWhenWithoutHeader() throws JsonProcessingException {
        ClientSaveBoardRequestDto clientSaveBoardRequestDto = ClientSaveBoardRequestDto.builder()
                .title("글 저장 제목 테스트")
                .content("글 저장 내용 테스트")
                .build();

        String body = objectMapper.writeValueAsString(clientSaveBoardRequestDto);
        headers.remove("Authentication");
        ResponseEntity<String> response = exchange(headers, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String resultBody =  dc.read("$.body");


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("권한이 없는 회원입니다.");
        assertThat(resultBody).isEqualTo(null);
    }


    @Test
    @DisplayName("잘못된 요청 글 저장 테스트")
    public void testSaveBoardWhenInvalid() throws JsonProcessingException {
        System.out.println("000000000000000000000000");
        ClientSaveBoardRequestDto clientSaveBoardRequestDto = ClientSaveBoardRequestDto.builder()
                .title("글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트" +
                        "글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트" +
                        "글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트글 저장 제목 테스트")
                .content("글 저장 내용 테스트")
                .build();
        System.out.println("11111111111111111111111111111");
        headers.set("Authentication", " Realtor 1");

        String body = objectMapper.writeValueAsString(clientSaveBoardRequestDto);

        ResponseEntity<String> response = exchange(headers, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String resultBody = dc.read("$.body");
        System.out.println("2222222222222222222222222222");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("{title=크기가 1에서 50 사이여야 합니다}");
        assertThat(resultBody).isEqualTo(null);

    }


    private ResponseEntity<String> exchange(HttpHeaders headers, String body){
        return rt.exchange(
                "/api/v1/community/board",
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class
        );
    }
}
