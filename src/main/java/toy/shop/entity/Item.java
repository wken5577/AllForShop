package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence")
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(generator = "item_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImages> itemImages = new ArrayList<>();

    private String name;

    private String itemInfo;

    private int price;

    private int quantity;

    public Item(Category category, String name, int price, int quantity, User user, List<ItemImages> itemImages, String itemInfo) {
        this.user = user;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.itemImages = itemImages;
        this.itemInfo = itemInfo;

        for (ItemImages itemImage : itemImages) {
            itemImage.setItem(this);
        }

    }

    protected void addQuantity(int quantity){
        this.quantity += quantity;
    }

    protected void minusQuantity(int quantity){
        this.quantity -= quantity;
    }

    public void update(Category category,String name, int price, int quantity){
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
