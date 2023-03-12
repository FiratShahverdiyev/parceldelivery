package com.parceldelivery.mscourier.service;

import com.parceldelivery.mscourier.entity.Location;
import com.parceldelivery.mscourier.entity.User;
import com.parceldelivery.mscourier.model.UserResponseDto;
import com.parceldelivery.mscourier.repository.UserLocationRepository;
import com.parceldelivery.mscourier.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;

    public Location getLastLocation(Integer userId) {
        return userLocationRepository.findByLastLocationByUserId(userId).orElseThrow().getLocation();
    }

    public Page<UserResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(this::toUserResponseDto);
    }

    private UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setAvailable(user.isAvailable());
        return userResponseDto;
    }

}
