package com.chatbot.adminlte.model.api;

import java.util.List;

public class SetUserRoleRequest {
    private String userName;
    private List<String> arr;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getArr() {
        return arr;
    }

    public void setArr(List<String> arr) {
        this.arr = arr;
    }
}
