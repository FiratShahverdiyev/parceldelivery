package com.parceldelivery.msdelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.msdelivery.common.DeliveryStatus;
import com.parceldelivery.msdelivery.common.PaginationRequest;
import com.parceldelivery.msdelivery.entity.Delivery;
import com.parceldelivery.msdelivery.error.EntityNotFoundException;
import com.parceldelivery.msdelivery.error.ProcessIsNotPossibleException;
import com.parceldelivery.msdelivery.mapper.DeliveryMapper;
import com.parceldelivery.msdelivery.model.DeliveryRequestDto;
import com.parceldelivery.msdelivery.model.DeliveryResponseDto;
import com.parceldelivery.msdelivery.model.UpdateCourierStatusRequestDto;
import com.parceldelivery.msdelivery.process.BaseDeliveryProcess;
import com.parceldelivery.msdelivery.process.DeliveryProcessFactory;
import com.parceldelivery.msdelivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryProcessFactory deliveryProcessFactory;
    private final DeliveryRepository deliveryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public Integer create(DeliveryRequestDto deliveryRequestDto) {
        Integer courierId = deliveryRequestDto.getCourierId();
        checkCourierIsAvailable(courierId);
        Delivery delivery = new Delivery();
        delivery.setCourierId(courierId);
        delivery.setOrderId(deliveryRequestDto.getOrderId());
        delivery = deliveryRepository.save(delivery);
        kafkaTemplate.send("create-delivery", delivery.getOrderId().toString());

        BaseDeliveryProcess baseDeliveryProcess = deliveryProcessFactory.create(DeliveryStatus.ON_COURIER);
        baseDeliveryProcess.tryProcess(delivery);

        return delivery.getId();
    }

    public Page<DeliveryResponseDto> getAll(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Delivery> deliveries = deliveryRepository.findAll(pageable);
        return deliveries.map(DeliveryMapper::toDto);
    }

    public DeliveryResponseDto getById(Integer id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Delivery.class, id));
        return DeliveryMapper.toDto(delivery);
    }

    public Page<DeliveryResponseDto> getPageByCourierId(Integer courierId,
                                                        PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Delivery> deliveries = deliveryRepository.findAllByCourierId(courierId, pageable);
        return deliveries.map(DeliveryMapper::toDto);
    }

    public void changeStatus(Integer id, DeliveryStatus status) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Delivery.class, id));
        BaseDeliveryProcess baseDeliveryProcess = deliveryProcessFactory.create(status);
        baseDeliveryProcess.tryProcess(delivery);
    }

    private void checkCourierIsAvailable(Integer courierId) {
        boolean isAvailable = deliveryRepository.courierIsAvailable(courierId).isEmpty();
        if (!isAvailable) {
            throw new ProcessIsNotPossibleException("Courier with: " + courierId + " is not available");
        }
    }

}
