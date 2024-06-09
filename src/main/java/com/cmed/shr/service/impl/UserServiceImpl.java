package com.cmed.shr.service.impl;

import com.cmed.shr.config.AuthConfig;
import com.cmed.shr.dto.request.UserRequestDto;
import com.cmed.shr.dto.response.UserResponseDto;
import com.cmed.shr.entity.UserEnitiy;
import com.cmed.shr.exception.UserAlreadyExistsException;
import com.cmed.shr.repository.UserRepo;
import com.cmed.shr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthConfig authConfig;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEnitiy user = userRepo.findByEmail(username).orElseThrow(()->new RuntimeException("User not found"));
        System.out.println("Retrived Data");
        System.out.println(user.getPassword()+" Retrived Password");
        System.out.println(user.getUsername());
        System.out.println("-----");
        return user;
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        List<UserEnitiy> userEnitiys = userRepo.findAll();
        return userEnitiys.stream().map(this::userEntityToUserRespDto).collect(Collectors.toList());


    }
    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<UserEnitiy> foundUser = this.userRepo.findByEmail(userRequestDto.getEmail());
        if (!foundUser.isPresent()) {
            UserEnitiy user = this.userReqDtoToUserEntity(userRequestDto);
            user.setPassword(authConfig.passwordEncoder().encode(user.getPassword()));
            UserEnitiy createdUser = userRepo.save(user);
            return this.userEntityToUserRespDto(createdUser);
        } else {
            throw new UserAlreadyExistsException("User with email " + userRequestDto.getEmail() + " already exists");
        }
    }


    public UserEnitiy userReqDtoToUserEntity(UserRequestDto userReqDto){
        return authConfig.modelMapper().map(userReqDto,UserEnitiy.class);
    }
    public UserResponseDto userEntityToUserRespDto(UserEnitiy user){
        return authConfig.modelMapper().map(user,UserResponseDto.class);
    }
}
