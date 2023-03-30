package com.changddao.Sjpashop.api;

import com.changddao.Sjpashop.entity.Address;
import com.changddao.Sjpashop.entity.Order;
import com.changddao.Sjpashop.entity.OrderStatus;
import com.changddao.Sjpashop.repository.OrderRepository;
import com.changddao.Sjpashop.repository.OrderSearch;
import com.changddao.Sjpashop.repository.OrderSimpleQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    @GetMapping("api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.dynamicFindAll(new OrderSearch());
        return all;
    }
    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2() {
        List<Order> orders = orderRepository.dynamicFindAll(new OrderSearch());
        List<SimpleOrderDto> collect = orders.stream().map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());
        return new Result(collect);
    }
    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> collect = orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4() {
        List<OrderSimpleQueryDto> osqd = orderRepository.findOrderDto();
        return new Result(osqd);


    }

    @Data
    @AllArgsConstructor
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getOrderStatus();
            address = order.getDelivery().getAddress();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
