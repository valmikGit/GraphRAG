package com.example.graphRAG.dto;

public class SignUpReq {
    private String email;

    private String password;


    public SignUpReq(){

    }

    public SignUpReq(String name,String email, String password,String phoneNum) {

        this.email = email;

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
