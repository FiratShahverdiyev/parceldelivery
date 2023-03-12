package com.parceldelivery.mscourier.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.mscourier.entity.Location;
import com.parceldelivery.mscourier.entity.User;
import com.parceldelivery.mscourier.entity.UserLocation;
import com.parceldelivery.mscourier.model.UpdateCourierStatusRequestDto;
import com.parceldelivery.mscourier.model.UpdateLocationRequestDto;
import com.parceldelivery.mscourier.repository.UserLocationRepository;
import com.parceldelivery.mscourier.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;

    @SneakyThrows
    @KafkaListener(topics = "update-location", groupId = "location-updaters")
    protected void updateLocationListener(String data) {
        UpdateLocationRequestDto updateLocationRequestDto = objectMapper.readValue(data,
                UpdateLocationRequestDto.class);
        UserLocation userLocation = new UserLocation();
        User user = userRepository.findById(updateLocationRequestDto.getCourierId()).orElseThrow();
        userLocation.setUser(user);
        userLocation.setLocation(new Location(updateLocationRequestDto.getLongitude(),
                updateLocationRequestDto.getLatitude()));
        userLocationRepository.save(userLocation);
    }

    @SneakyThrows
    @KafkaListener(topics = "update-courier-status", groupId = "courier-status-updaters")
    protected void updateCourierStatusListener(String data) {
        UpdateCourierStatusRequestDto requestDto = objectMapper.readValue(data,
                UpdateCourierStatusRequestDto.class);
        User user = userRepository.findById(requestDto.getCourierId()).orElseThrow();
        user.setAvailable(requestDto.isAvailable());
        userRepository.save(user);
    }

}
