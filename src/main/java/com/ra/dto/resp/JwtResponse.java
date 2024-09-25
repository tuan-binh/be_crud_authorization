package com.ra.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse
{
    private String accessToken;
    private final String type = "Bearer";
    private Long expired;
    private String fullName;
    private String email;
    private String phone;
    private Set<String> roles;
    private Boolean status;
}