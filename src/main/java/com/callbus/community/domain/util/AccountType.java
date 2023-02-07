package com.callbus.community.domain.util;

public enum AccountType {
    Lessor("Lessor"), Realtor("Realtor"), Lessee("Lessee"), ;

    private final String accountType;


    AccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType(){
        return accountType;
    }
}
