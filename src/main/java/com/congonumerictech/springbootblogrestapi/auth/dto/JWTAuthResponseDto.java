package com.congonumerictech.springbootblogrestapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
}
