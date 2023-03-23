package com.congonumerictech.springbootblogrestapi.auth.Service;

import com.congonumerictech.springbootblogrestapi.auth.dto.LoginDto;
import com.congonumerictech.springbootblogrestapi.auth.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register (RegisterDto registerDto);
}
