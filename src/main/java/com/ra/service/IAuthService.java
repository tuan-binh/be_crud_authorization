package com.ra.service;

import com.ra.dto.req.LoginRequest;
import com.ra.dto.req.RegisterRequest;
import com.ra.dto.resp.JwtResponse;
import com.ra.exception.CustomException;
import com.ra.security.principle.MyUserDetails;

public interface IAuthService {
    JwtResponse login(LoginRequest loginRequest) throws CustomException;

    void register(RegisterRequest registerRequest) throws CustomException;

    MyUserDetails getCurrentUser();
}
