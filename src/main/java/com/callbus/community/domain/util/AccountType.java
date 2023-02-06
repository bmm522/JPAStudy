package com.callbus.community.domain.util;

public enum AccountType {
    LESSOR("LESSOR"), REALTOR("REALTOR"), LESSEE("LESSEE");

    private final String accountType;


    AccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType(){
        return accountType;
    }
}
