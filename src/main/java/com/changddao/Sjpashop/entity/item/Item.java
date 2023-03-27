package com.changddao.Sjpashop.entity.item;

import com.changddao.Sjpashop.entity.Category;
import com.changddao.Sjpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직

    //재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    //재고감소
    public void removeStock(int quantity) throws NotEnoughStockException {
        int restQuantity =this.stockQuantity -= quantity;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restQuantity;
    }


}
