package com.callbus.community.controller;

import com.callbus.community.controller.dto.request.ClientBoardUpdateRequestDto;
import com.callbus.community.repository.BoardRepository;
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
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateBoardApiControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestRestTemplate rt;

    private final HttpHeaders headers = StaticStore.getHeaders();
    private final ObjectMapper objectMapper = StaticStore.getObjectMapper();




    @BeforeEach
    @Sql("classpath:schema.sql")
    @Sql("classpath:data.sql")
    public void setup(){
        rt.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
    @Test
    @DisplayName("정상적인 요청 글 수정 테스트")
    public void testUpdateBoardSuccess() throws JsonProcessingException {

        Long boardId = 1L;
        ClientBoardUpdateRequestDto clientBoardUpdateRequestDto = ClientBoardUpdateRequestDto.builder()
                .title("글 수정 후 제목 테스트")
                .content("글 수정 후 내용 테스트")
                .build();

        headers.set("Authentication", " Realtor 1");
        String body = objectMapper.writeValueAsString(clientBoardUpdateRequestDto);

        ResponseEntity<String> response = exchange(boardId, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String title =  dc.read("$.body.title");
        Integer boardIdJson = dc.read("$.body.boardId");


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(code).isEqualTo(1);
        assertThat(msg).isEqualTo("글 수정 성공");
        assertThat(title).isEqualTo("글 수정 후 제목 테스트");
        assertThat(boardIdJson).isEqualTo(1);
    }

    @Test
    @DisplayName("잘못된 요청 글 수정 테스트")
    public void testUpdateBoardWhenInvalid() throws JsonProcessingException {

        Long boardId = 1L;
        ClientBoardUpdateRequestDto boardupdateRequestDtoClient = ClientBoardUpdateRequestDto.builder()
                .title("글 수정 후 제목 테스트글 수정 후 제목 테스트글 " +
                        "수정 후 제목 테스트글 수정 후 제목 테스트글 수정 " +
                        "후 제목 테스트글 수정 후 제목 테스트글 수정 " +
                        "후 제목 테스트글 수정 후 제목 테스트글 수정 후 " +
                        "제목 테스트글 수정 후 제목 테스트글 수정 후 제목 ")
                .content("글 수정 후 내용 테스트")
                .build();

        headers.set("Authentication", " Realtor 1");
        String body = objectMapper.writeValueAsString(boardupdateRequestDtoClient);

        ResponseEntity<String> response = exchange(boardId, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");

        System.out.println(msg);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("{title=크기가 1에서 50 사이여야 합니다}");

    }

    @Test
    @DisplayName("잘못된 글 번호 수정 테스트")
    public void testUpdateBoardWrongBoardId() throws JsonProcessingException {

        Long boardId = 5L;
        ClientBoardUpdateRequestDto clientBoardUpdateRequestDto = ClientBoardUpdateRequestDto.builder()
                .title("글 수정 후 제목 테스트")
                .content("글 수정 후 내용 테스트")
                .build();

        headers.set("Authentication", " Realtor 1");
        String body = objectMapper.writeValueAsString(clientBoardUpdateRequestDto);

        ResponseEntity<String> response = exchange(boardId, body);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(code).isEqualTo(-1);
        assertThat(msg).isEqualTo("해당 게시글의 정보를 찾을 수 없습니다.");
    }

    private ResponseEntity<String> exchange(Long boardId, String body){
        return rt.exchange(
                "/api/v1/community/board/"+boardId,
                HttpMethod.PATCH,
                new HttpEntity<>(body, headers),
                String.class
        );
    }


}
