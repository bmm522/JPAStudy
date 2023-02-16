package com.personal.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelectBoardApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    private final HttpHeaders headers = StaticStore.getHeaders();

    private final ObjectMapper objectMapper = StaticStore.getObjectMapper();

    @Test
    @DisplayName("글 목록 불러오기 테스트")
    public void getBoardListTest(){
        headers.set("Authentication", " Realtor 1");
        postLikeForTest(headers,2L);
        ResponseEntity<String> response = exchange(headers);

        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String msg = dc.read("$.msg");
        String updateDate = dc.read("$.body.items[0].updateDate");
        String writer = dc.read("$.body.items[0].writer");
        String havePermission = dc.read("$.body.items[0].targetMemberModificationPermission");
        String haveNotPermission = dc.read("$.body.items[1].targetMemberModificationPermission");
        String checkedLike = dc.read("$.body.items[1].targetMemberIsLike");
        String unCheckedLike = dc.read("$.body.items[0].targetMemberIsLike");
        int likeCount = dc.read("$.body.items[1].likeCount");
        int likeCount2 = dc.read("$.body.items[0].likeCount");

        assertThat(code).isEqualTo(1);
        assertThat(msg).isEqualTo("글 목록 보기 성공");
        assertThat(updateDate).isEqualTo("N");
        assertThat(writer).isEqualTo("김지인(공인중개사)");
        assertThat(havePermission).isEqualTo("Y");
        assertThat(haveNotPermission).isEqualTo("N");
        assertThat(checkedLike).isEqualTo("Y");
        assertThat(unCheckedLike).isEqualTo("N");
        assertThat(likeCount).isEqualTo(1);
        assertThat(likeCount2).isEqualTo(0);
    }

    @Test
    @DisplayName("외부 사용자 글 목록 불러오기 테스트")
    public void getBoardListWhenExternalUserTest(){
        headers.remove("Authentication");
        ResponseEntity<String> response = exchange(headers);

        DocumentContext dc = JsonPath.parse(response.getBody());
        String checkedLike = dc.read("$.body.items[1].targetMemberIsLike");
        String unCheckedLike = dc.read("$.body.items[0].targetMemberIsLike");

        assertThat(checkedLike).isEqualTo("EX");
        assertThat(unCheckedLike).isEqualTo("EX");
    }

    private ResponseEntity<String> exchange(HttpHeaders headers) {
        return rt.exchange(
                "/api/v1/community/boards",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
    }

    private ResponseEntity<String> postLikeForTest(HttpHeaders headers,Long boardId){
        return rt.exchange(
                "/api/v1/community/board/like/"+boardId,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
    }

}
