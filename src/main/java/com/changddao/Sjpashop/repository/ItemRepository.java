package com.changddao.Sjpashop.repository;

import com.changddao.Sjpashop.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
