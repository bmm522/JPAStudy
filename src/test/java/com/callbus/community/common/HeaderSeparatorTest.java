package com.callbus.community.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HeaderSeparatorTest {


    private final HeaderSeparator headerSeparator= HeaderSeparator.getInstance();

    @DisplayName("헤더값에서 유저 번호만 가져오기")
    @Test
    public void getIdFromAuthenticationTest(){
        String authentication = "Realtor 47";
        String authentication2 = "Lessor 21";
        String authentication3 = "Lessee 562";

        Long id = headerSeparator.getIdFromAuthentication(authentication);
        Long id2 = headerSeparator.getIdFromAuthentication(authentication2);
        Long id3 = headerSeparator.getIdFromAuthentication(authentication3);

        assertThat(id).isEqualTo(47);
        assertThat(id2).isEqualTo(21);
        assertThat(id3).isEqualTo(562);
    }
}
