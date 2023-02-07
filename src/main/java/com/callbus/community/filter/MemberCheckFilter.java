package com.callbus.community.filter;


import com.callbus.community.controller.dto.request.MemberReqDto;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MemberCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String authentication = ((HttpServletRequest) request).getHeader("Authentication");

            MemberReqDto memberReqDto = getDto(getInfoFromAuthentication(authentication, 1), getInfoFromAuthentication(authentication, 0));
            request.setAttribute("memberReqDto", memberReqDto);
            filterChain.doFilter(request, response);

        } catch (Exception e){

            MemberReqDto memberReqDto = getDto("0", "externalUser");
            request.setAttribute("memberReqDto", memberReqDto);
            filterChain.doFilter(request, response);
        }

    }

    public MemberReqDto getDto(String memberId, String accountType){
        return MemberReqDto.builder()
                .memberId(memberId)
                .accountType(accountType)
                .build();
    }

    public String getInfoFromAuthentication(String authorization, int index){
        return authorization.split(" ")[index];
    }
}