package com.example.graphRAG.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponse {
    private String jwt;

    private Long id;

    public AuthResponse(){

    }

    public AuthResponse(String jwt, Long id) {
        this.jwt = jwt;
        this.id = id;

    }

}
