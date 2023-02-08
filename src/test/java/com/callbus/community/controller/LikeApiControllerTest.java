package com.callbus.community.controller;

import com.callbus.community.domain.Member;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.domain.util.Status;
import com.callbus.community.repository.BoardRepository;
import com.callbus.community.repository.LikeRepository;
import com.callbus.community.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;

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

//
//    @Test
//    @DisplayName("정상적인 좋아요 저장 테스트")
//    public
}
