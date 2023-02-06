package com.callbus.community.filter;


import com.callbus.community.controller.dto.request.MemberReqDto;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class MemberCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            String authentication = ((HttpServletRequest) request).getHeader("Authentication");

            MemberReqDto memberReqDto = MemberReqDto.builder()
                    .memberId(getInfoFromAuthentication(authentication, 1))
                    .accountType(getInfoFromAuthentication(authentication, 0))
                    .build();
            request.setAttribute("memberReqDto", memberReqDto);

            filterChain.doFilter(request, response);
        } catch (Exception e){
            request.setAttribute("accountType", null);
            request.setAttribute("memberId", null);
            filterChain.doFilter(request, response);
        }

    }

    public String getInfoFromAuthentication(String authorization, int index){
        return authorization.split(" ")[index];
    }
}
