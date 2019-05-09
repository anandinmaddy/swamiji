package com.sastra.im037.sastraprakasika.Model;

public class AccountModel {
    String name;
    int type;

    public AccountModel(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
