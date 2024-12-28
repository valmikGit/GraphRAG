package com.example.graphRAG.service.auth;

import com.example.graphRAG.dto.SignUpReq;
import com.example.graphRAG.dto.UserDto;
import com.example.graphRAG.entity.UserEntity;
import com.example.graphRAG.exception.UserAlreadyExistsException;
import com.example.graphRAG.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createuser(SignUpReq sign) {
        if (userRepo.findByEmailId(sign.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email = " + sign.getEmail() + " already exists.");
        }
        UserEntity user=new UserEntity();
        user.setEmailId(sign.getEmail());
        user.setPassword(passwordEncoder.encode(sign.getPassword()));
        UserDto userDto=new UserDto();
        UserEntity userEntity=userRepo.save(user);
        userDto.setId(userEntity.getId());
        return userDto;
    }
}
