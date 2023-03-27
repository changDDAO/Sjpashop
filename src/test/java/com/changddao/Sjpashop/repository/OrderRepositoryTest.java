package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.*;
import com.changddao.Sjpashop.entity.item.Book;
import com.changddao.Sjpashop.entity.item.Item;
import com.changddao.Sjpashop.service.ItemService;
import com.changddao.Sjpashop.service.MemberService;
import com.changddao.Sjpashop.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    @Transactional
    public void dynamicFindAll() throws Exception {
        //given
        Member member = new Member("회원1",new Address("대구","청수로","261"));
        Member member1 = new Member("회원2",new Address("대구","청수로","261"));
        Member member2 = new Member("회원3",new Address("대구","청수로","261"));
        memberService.join(member);
        memberService.join(member1);
        memberService.join(member2);
        Item item = new Book("윤창호","1234");
        Item item1 = new Book("김진아","1234");
        Item item2 = new Book("윤종호","1234");
        item.setPrice(10000);
        item.setStockQuantity(10);
        item1.setPrice(30000);
        item1.setStockQuantity(40);
        item2.setPrice(20000);
        item2.setStockQuantity(30);
        itemService.saveItem(item);
        itemService.saveItem(item1);
        itemService.saveItem(item2);
        int orderCount = 2;
        Long order = orderService.order(member.getId(), item.getId(), orderCount);
        Long order2 = orderService.order(member1.getId(), item1.getId(), orderCount);
        //when
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("회원1");
        List<Order> findOrder = orderService.findOrders(orderSearch);

        //then
        assertThat(findOrder.size()).isEqualTo(1);
        assertThat(findOrder).extracting("OrderStatus").containsExactly(OrderStatus.ORDER);
        assertThat(findOrder.get(0).getMember().getUsername()).isEqualTo("회원1");

    }

}