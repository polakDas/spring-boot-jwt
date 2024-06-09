package com.cmed.shr.service;

import com.cmed.shr.dto.request.UserRequestDto;
import com.cmed.shr.dto.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserResponseDto> getAllUser();
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
