package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom{

}
