package com.changddao.Sjpashop.repository;

import lombok.Data;

@Data
public class OrderItemQueryDto {

    private String itemName;
    private int orderPrice;
    private int count;

}