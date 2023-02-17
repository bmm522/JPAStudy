package com.personal.community.repository.entity.util;

public enum Status {
    Y("Y"),N("N");

    private final String status;

    Status(String status) {this.status = status;}

    public String getStatus() {return status;}
}
