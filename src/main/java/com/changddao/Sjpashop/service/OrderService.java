package com.changddao.Sjpashop.service;

import com.changddao.Sjpashop.entity.Delivery;
import com.changddao.Sjpashop.entity.Member;
import com.changddao.Sjpashop.entity.Order;
import com.changddao.Sjpashop.entity.OrderItem;
import com.changddao.Sjpashop.entity.item.Item;
import com.changddao.Sjpashop.repository.ItemRepository;
import com.changddao.Sjpashop.repository.MemberRepository;
import com.changddao.Sjpashop.repository.OrderRepository;
import com.changddao.Sjpashop.repository.OrderSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository= memberRepository;
        this.itemRepository = itemRepository;
    }
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔터티 조회
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);
        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }
    //@주문취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.cancel();
    }
    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.dynamicFindAll(orderSearch);
    }
}
