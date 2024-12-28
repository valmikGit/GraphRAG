package com.example.graphRAG.service.auth;

import com.example.graphRAG.dto.SignUpReq;
import com.example.graphRAG.dto.UserDto;

public interface AuthService {
    UserDto createuser(SignUpReq sign);
}
