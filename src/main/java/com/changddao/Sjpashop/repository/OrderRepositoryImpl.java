package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.*;
import com.changddao.Sjpashop.entity.item.QItem;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.changddao.Sjpashop.entity.QDelivery.*;
import static com.changddao.Sjpashop.entity.QMember.*;
import static com.changddao.Sjpashop.entity.QOrder.*;
import static com.changddao.Sjpashop.entity.QOrderItem.*;
import static com.changddao.Sjpashop.entity.item.QItem.*;

public class OrderRepositoryImpl implements OrderRepositoryCustom{
    JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    public List<Order> dynamicFindAll(OrderSearch orderSearch){
        List<Order> orderList = queryFactory.select(order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
        return orderList;

    }

    @Override
    public List<Order> findAllWithMemberDelivery() {
        List<Order> orders = queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .fetch();
    return orders;

    }

    @Override
    public List<OrderSimpleQueryDto> findOrderDto() {
        List<OrderSimpleQueryDto> result = queryFactory.select(Projections.constructor(OrderSimpleQueryDto.class, order.id,
                        member.username, order.orderDate, order.orderStatus, delivery.address))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .fetch();
        return result;
    }

    @Override
    public List<Order> findAllWithItem() {
        List<Order> result = queryFactory.selectFrom(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(order.orderItems, orderItem).fetchJoin()
                .join(orderItem.item, item).fetchJoin()
                .distinct().fetch();
        return result;
    }



    private BooleanExpression nameLike(String memberName) {
        if(!StringUtils.hasText(memberName))
            return null;
        return member.username.like(memberName);

    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if(orderStatus==null)
            return null;
        return order.orderStatus.eq(orderStatus);
    }

}
