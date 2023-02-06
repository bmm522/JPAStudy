package com.callbus.community.util;

import java.util.Arrays;

public class HeaderSeparator {

    private static HeaderSeparator headerSeparator = new HeaderSeparator();

    public static HeaderSeparator getInstance(){
        return headerSeparator;
    }

    private HeaderSeparator(){}

    public Long getIdFromAuthorization(String authorization){
        return Long.parseLong(authorization.split(" ")[1]);
    }

}
