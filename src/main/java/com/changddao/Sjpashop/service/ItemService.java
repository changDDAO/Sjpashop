package com.changddao.Sjpashop.service;

import com.changddao.Sjpashop.entity.item.Item;
import com.changddao.Sjpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Item findOne(Long id) {
        Item findItem = itemRepository.findById(id).orElse(null);
        return findItem;
    }


}
