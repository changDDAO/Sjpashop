package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.Order;
import com.changddao.Sjpashop.entity.OrderStatus;
import com.changddao.Sjpashop.entity.QMember;
import com.changddao.Sjpashop.entity.QOrder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.changddao.Sjpashop.entity.QMember.*;
import static com.changddao.Sjpashop.entity.QOrder.*;

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