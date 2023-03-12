package com.parceldelivery.msorder.service;

import com.parceldelivery.msorder.common.OrderStatus;
import com.parceldelivery.msorder.common.PaginationRequest;
import com.parceldelivery.msorder.entity.Order;
import com.parceldelivery.msorder.error.EntityNotFoundException;
import com.parceldelivery.msorder.mapper.OrderMapper;
import com.parceldelivery.msorder.model.LocationRequestDto;
import com.parceldelivery.msorder.model.OrderRequestDto;
import com.parceldelivery.msorder.model.OrderResponseDto;
import com.parceldelivery.msorder.process.BaseOrderProcess;
import com.parceldelivery.msorder.process.OrderProcessFactory;
import com.parceldelivery.msorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderProcessFactory orderProcessFactory;

    @Transactional
    public void changeStatus(Integer id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
        BaseOrderProcess orderProcess = orderProcessFactory.create(status);
        orderProcess.tryProcess(order);
    }

    @Transactional
    public void cancel(Integer id, Integer userId) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
        checkIsOwner(userId, order);
        BaseOrderProcess orderProcess = orderProcessFactory.create(OrderStatus.CANCELED);
        orderProcess.tryProcess(order);
    }

    @Transactional
    public Integer create(OrderRequestDto requestDto) {
        Order order = orderMapper.toEntity(requestDto);
        BaseOrderProcess orderProcess = orderProcessFactory.create(OrderStatus.IN_PROGRESS);
        orderProcess.tryProcess(order);
        return orderRepository.save(order).getId();
    }

    public Page<OrderResponseDto> getPage(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        return orderRepository.findAll(pageable).map(orderMapper::toResponseDto);
    }

    public OrderResponseDto getById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
        return orderMapper.toResponseDto(order);
    }

    public Page<OrderResponseDto> getPageByUserId(Integer userId, PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        return orderRepository.findAllByUserId(userId, pageable).map(orderMapper::toResponseDto);
    }

    @Transactional
    public void changeDestination(Integer id, LocationRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
        checkIsOwner(requestDto.getUserId(), order);
        if (userCanChangeDestination(order.getStatus())) {
            orderMapper.updateDestination(order, requestDto);
            orderRepository.save(order);
        }
    }

    private boolean userCanChangeDestination(OrderStatus status) {
        return OrderStatus.IN_PROGRESS.equals(status) || OrderStatus.ACCEPTED.equals(status);
    }

    private void checkIsOwner(Integer userId, Order order) {
        if (!order.getUserId().equals(userId))
            throw new RuntimeException();
    }

}
