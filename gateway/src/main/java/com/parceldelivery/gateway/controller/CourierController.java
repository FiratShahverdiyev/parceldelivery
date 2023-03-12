package com.parceldelivery.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.gateway.client.PaginationRequest;
import com.parceldelivery.gateway.client.admin.MsAuthAdminClient;
import com.parceldelivery.gateway.client.courier.LoginClientRequestDto;
import com.parceldelivery.gateway.client.courier.MsCourierClient;
import com.parceldelivery.gateway.client.courier.ProfileClientResponseDto;
import com.parceldelivery.gateway.client.courier.RegisterCourierRequestDto;
import com.parceldelivery.gateway.client.courier.UpdateLocationClientRequestDto;
import com.parceldelivery.gateway.dto.UpdateLocationRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/couriers")
public class CourierController {

    private final MsAuthAdminClient msAuthAdminClient;
    private final MsCourierClient msCourierClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping("/auth/login")
    public Object login(@RequestBody LoginClientRequestDto requestDto) {
        return msCourierClient.login(requestDto);
    }

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterCourierRequestDto requestDto, HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        msCourierClient.register(requestDto);
    }

    @PostMapping("/auth/verify")
    public Object verify(HttpServletRequest httpServletRequest) {
        return msCourierClient.verify(httpServletRequest.getHeader("Authorization"));
    }

    @ApiOperation(value = "Update location with longitude and latitude",
            notes = "Courier can update self location by this. You must be login as a COURIER!")
    @SneakyThrows
    @PutMapping("/location")
    public void updateLocation(@RequestBody UpdateLocationRequestDto requestDto,
                               HttpServletRequest httpServletRequest) {
        ProfileClientResponseDto profileClientResponseDto = msCourierClient
                .verify(httpServletRequest.getHeader("Authorization"));
        UpdateLocationClientRequestDto updateLocationClientRequestDto = new UpdateLocationClientRequestDto();
        updateLocationClientRequestDto.setCourierId(profileClientResponseDto.getId());
        updateLocationClientRequestDto.setLongitude(requestDto.getLongitude());
        updateLocationClientRequestDto.setLatitude(requestDto.getLatitude());
        kafkaTemplate.send("update-location", objectMapper.writeValueAsString(updateLocationClientRequestDto));
    }

    @ApiOperation(value = "Get couriers last location by courier id",
            notes = "Only admins can track delivery parcels by courier location. You must be login as a ADMIN!")
    @GetMapping("/{id}/last-location")
    public Object getLastLocationByCourierId(@PathVariable Integer id,
                                             HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        return msCourierClient.getLastLocationByUserId(id);
    }

    @ApiOperation(value = "Get all couriers with their available status",
            notes = "Only admins can see all couriers. You must be login as a ADMIN!")
    @GetMapping
    public Object getAll(PaginationRequest paginationRequest,
                         HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        return msCourierClient.getAll(paginationRequest.getPage(), paginationRequest.getSize());
    }


}
