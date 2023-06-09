package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<Order> dynamicFindAll(OrderSearch orderSearch);

    public List<Order> findAllWithMemberDelivery();

    public List<OrderSimpleQueryDto> findOrderDto();

    public List<Order> findAllWithItem();

    public List<OrderQueryDto> findOrderQueryDto();

    public List<OrderItemQueryDto> findOrderItemQueryDto(Long orderId);


}
