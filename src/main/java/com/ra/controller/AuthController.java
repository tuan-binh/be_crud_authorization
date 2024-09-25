package com.ra.controller;

import com.ra.dto.req.LoginRequest;
import com.ra.dto.req.RegisterRequest;
import com.ra.exception.CustomException;
import com.ra.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController
{

    private final IAuthService authService;

    /**
     * @param loginRequest LoginRequest
     * @apiNote handle login with object { email, password }
     */
    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody LoginRequest loginRequest) throws CustomException
    {
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    /**
     * @param registerRequest RegisterRequest
     * @apiNote handle register with object { fullName, email, password }
     */
    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@Valid @RequestBody RegisterRequest registerRequest) throws CustomException
    {
        authService.register(registerRequest);
        return ResponseEntity.created(URI.create("api/v1/auth/register")).body("Đăng ký thành công");
    }

}

