package com.parceldelivery.msauthadmin.service;

import com.parceldelivery.msauthadmin.common.SecurityUtil;
import com.parceldelivery.msauthadmin.common.TokenProvider;
import com.parceldelivery.msauthadmin.entity.User;
import com.parceldelivery.msauthadmin.model.LoginRequestDto;
import com.parceldelivery.msauthadmin.model.ProfileResponseDto;
import com.parceldelivery.msauthadmin.model.RefreshTokenRequestDto;
import com.parceldelivery.msauthadmin.model.RegisterUserRequestDto;
import com.parceldelivery.msauthadmin.model.UserDetails;
import com.parceldelivery.msauthadmin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public String login(LoginRequestDto loginRequestDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    public void register(RegisterUserRequestDto registerUserRequestDto) {
        User user = new User();
        user.setUsername(registerUserRequestDto.getUsername());
        user.setFullName(registerUserRequestDto.getFullName());
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        userRepository.save(user);
    }

    public String refresh(RefreshTokenRequestDto requestDto) {
        return tokenProvider.createRefreshToken(requestDto.getToken());
    }

    public ProfileResponseDto getProfile() {
        UserDetails userDetails = securityUtil.getPrincipal();
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        profileResponseDto.setUsername(userDetails.getUsername());
        profileResponseDto.setFullName(userDetails.getFullName());
        return profileResponseDto;
    }

}
