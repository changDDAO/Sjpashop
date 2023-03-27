package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName; //회원이름
    private OrderStatus orderStatus; //주문 상태
}
