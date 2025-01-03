package com.example.graphRAG.controller;

import com.example.graphRAG.dto.AuthResponse;
import com.example.graphRAG.dto.LoginReq;
import com.example.graphRAG.dto.SignUpReq;
import com.example.graphRAG.dto.UserDto;
import com.example.graphRAG.entity.UserEntity;
import com.example.graphRAG.exception.UserAlreadyExistsException;
import com.example.graphRAG.repository.UserRepo;
import com.example.graphRAG.service.auth.AuthService;
import com.example.graphRAG.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;
    private final UserRepo userRepo;

    public UserController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils, UserRepo userRepo) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.userRepo = userRepo;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpReq sign){
        System.out.println(sign);
        try {
            UserDto userDto=authService.createuser(sign);
            if(userDto==null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(userDto);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody LoginReq authRequest, HttpServletResponse response) throws IOException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
            );
        }
        catch (BadCredentialsException badCredentialsException){
            throw new BadCredentialsException("Incorrect username or password");
        }
        catch (DisabledException d){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"User not active");
        }
        final UserDetails userDetails= userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt= jwtUtils.generateToken(userDetails.getUsername());

        Optional<UserEntity> user=userRepo.findByEmailId(userDetails.getUsername());
        AuthResponse authenticationResponse= new AuthResponse();
        if(user.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setId(user.get().getId());

        }
        return authenticationResponse;
    }
}
