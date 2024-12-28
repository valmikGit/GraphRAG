package com.example.graphRAG.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpReq {
    private String email;
    private String password;

    public SignUpReq(){}
    public SignUpReq(String name,String email, String password,String phoneNum) {
        this.email = email;
        this.password = password;
    }
}
