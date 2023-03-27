package com.changddao.Sjpashop.service;

import com.changddao.Sjpashop.entity.Address;
import com.changddao.Sjpashop.entity.Member;
import com.changddao.Sjpashop.entity.Order;
import com.changddao.Sjpashop.entity.OrderStatus;
import com.changddao.Sjpashop.entity.item.Book;
import com.changddao.Sjpashop.entity.item.Item;
import com.changddao.Sjpashop.exception.NotEnoughStockException;
import com.changddao.Sjpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member("회원1",new Address("대구","청수로","261"));
        memberService.join(member);
        Item item = new Book("윤창호","1234");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemService.saveItem(item);
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 3);
        //then
        Order order = orderRepository.findById(orderId).orElse(null);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getTotalPrice()).isEqualTo(30000);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(item.getStockQuantity()).isEqualTo(7);
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = new Member("회원1",new Address("대구","청수로","261"));
        memberService.join(member);
        Item item = new Book("윤창호","1234");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemService.saveItem(item);
        int orderCount = 2;

        Long order = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(order);
        Optional<Order> findOrder = orderRepository.findById(order);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(10);
        assertThat(findOrder.get().getOrderStatus()).isEqualTo(OrderStatus.CANCEL);

    }

    @Test
    public void 재고수량초과() throws Exception {
        //given
        Member member = new Member("회원1",new Address("대구","청수로","261"));
        memberService.join(member);
        Item item = new Book("윤창호","1234");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemService.saveItem(item);
        //when

        //then
        assertThrows(NotEnoughStockException.class,()->orderService.order(member.getId(), item.getId(), 11));

    }


}