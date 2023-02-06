package com.callbus.community.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.*;
import static com.callbus.community.util.HeaderSeparator.getInstance;
public class HeaderSeparatorTest {


    private final HeaderSeparator headerSeparator= HeaderSeparator.getInstance();

    @DisplayName("헤더값에서 유저 번호만 가져오기")
    @Test
    public void getIdFromAuthorizationTest(){
        String authorization = "Realtor 47";
        String authorization2 = "Lessor 21";
        String authorization3 = "Lessee 562";

        Long id = headerSeparator.getIdFromAuthorization(authorization);
        Long id2 = headerSeparator.getIdFromAuthorization(authorization2);
        Long id3 = headerSeparator.getIdFromAuthorization(authorization3);

        assertThat(id).isEqualTo(47);
        assertThat(id2).isEqualTo(21);
        assertThat(id3).isEqualTo(562);
    }
}
