package com.callbus.community.common;

public class MemberChecker {

    private static MemberChecker memberChecker = new MemberChecker();

    public static MemberChecker getInstance(){
        return memberChecker;
    }

    private MemberChecker(){}




}
