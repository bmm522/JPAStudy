package com.callbus.community.common;

public class HeaderSeparator {

    private static HeaderSeparator headerSeparator = new HeaderSeparator();

    public static HeaderSeparator getInstance(){
        return headerSeparator;
    }

    private HeaderSeparator(){}

    public Long getIdFromAuthentication(String authorization){
        return Long.parseLong(authorization.split(" ")[1]);
    }

}
