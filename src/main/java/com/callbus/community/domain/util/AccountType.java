package com.callbus.community.domain.util;

public enum AccountType {
    LESSOR("Lessor"), REALTOR("Realtor"), LESSEE("Lessee");

    private final String accountType;


    AccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType(){
        return accountType;
    }
}
