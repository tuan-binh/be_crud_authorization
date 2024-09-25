package com.ra.service.impl;

import com.ra.constants.RoleName;
import com.ra.dto.req.LoginRequest;
import com.ra.dto.req.RegisterRequest;
import com.ra.dto.resp.JwtResponse;
import com.ra.exception.CustomException;
import com.ra.model.Roles;
import com.ra.model.Users;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JwtProvider;
import com.ra.security.principle.MyUserDetails;
import com.ra.service.IAuthService;
import com.ra.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager manager;
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IRoleService roleService;
    private final JwtProvider jwtProvider;

    @Value("${jwt.expired.access}")
    private Long EXPIRED;


    @Override
    public JwtResponse login(LoginRequest loginRequest) throws CustomException {
        Authentication authentication;
        try {
            authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new CustomException("Tài khoản hoặc mật khẩu sai", HttpStatus.BAD_REQUEST);
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (!userDetails.getUsers().getStatus()) {
            throw new CustomException("Tài khoản của bạn đã bị khóa", HttpStatus.BAD_REQUEST);
        }
        return JwtResponse.builder()
                .accessToken(jwtProvider.generateToken(userDetails.getUsername()))
                .expired(EXPIRED)
                .fullName(userDetails.getUsers().getFullName())
                .email(userDetails.getUsers().getEmail())
                .phone(userDetails.getUsers().getPhone())
                .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .status(userDetails.getUsers().getStatus())
                .build();
    }

    @Override
    public void register(RegisterRequest registerRequest) throws CustomException {
        Set<Roles> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        Users users = Users.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .status(true)
                .roles(roles)
                .build();
        userRepository.save(users);
    }

    @Override
    public MyUserDetails getCurrentUser() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
