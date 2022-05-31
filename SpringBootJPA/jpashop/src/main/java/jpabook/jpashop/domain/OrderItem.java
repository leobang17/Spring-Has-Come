package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 당시의 가격
    private int count; // 주문 당시의 수량


    // == 생성 메서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 넘어온 것 만큼 재고를 까줘야 함.
        item.removeStock(count);

        return orderItem;
    }

    protected OrderItem() {}

    // == 비즈니스 로직 == //
    public void cancel() {
        // 재고 수량을 원상 복귀해준다. (구매 시점의 수량인 count 만큼 다시 늘려준다.)
        getItem().addStock(count);
    }

    // == 조회 로직 == //
    /*
    * 주문 상품 전체 가격 조회
    * */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
