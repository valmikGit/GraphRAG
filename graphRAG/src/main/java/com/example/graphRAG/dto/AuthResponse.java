package com.example.graphRAG.dto;

public class AuthResponse {
    private String jwt;

    private Long id;

    public AuthResponse(){

    }

    public AuthResponse(String jwt, Long id) {
        this.jwt = jwt;
        this.id = id;

    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
