package com.parceldelivery.msdelivery.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.msdelivery.common.DeliveryStatus;
import com.parceldelivery.msdelivery.entity.Delivery;
import com.parceldelivery.msdelivery.model.UpdateCourierStatusRequestDto;
import com.parceldelivery.msdelivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCancelProcess extends BaseDeliveryProcess {

    private final DeliveryRepository deliveryRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @Override
    protected void process(Delivery delivery) {
        delivery.setStatus(DeliveryStatus.CANCELED);
        deliveryRepository.save(delivery);

        UpdateCourierStatusRequestDto courierStatusRequestDto = new UpdateCourierStatusRequestDto();
        courierStatusRequestDto.setCourierId(delivery.getCourierId());
        courierStatusRequestDto.setAvailable(true);
        kafkaTemplate.send("update-courier-status", objectMapper.writeValueAsString(courierStatusRequestDto));
    }

    @Override
    protected void checkNextProcessIsPossible(Delivery delivery) {

    }

}
