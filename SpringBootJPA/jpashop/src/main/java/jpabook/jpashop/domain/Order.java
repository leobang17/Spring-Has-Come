package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.event.spi.DeleteEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status;

    // 연관관계 편의 메서드
    // 핵심적으로 control하는 쪽이 연관관계 메서드를 들고 있는게 좋다
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // == 생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    protected Order() {}

    // == 비즈니스 로직 == //
    // 엔티티 안에 핵심 비즈니스 로직이 있는 개발 스타일
    //  -> 도메인 모델 패턴 (Domain model pattern)
    // 서비스 계층은 단순히 엔티티에 필요한 요청을 위임(delegate)하는 역할

    // 반대로 entity 에 비즈니스 로직이 거의 없고, service layer 에서 대부분의 logic 을 구현하는 것을 '트랙잭셔널 스크립트 패턴' 이라고 함.
    // jpa 와 같은 orm을 이용하게 되면 도메인 모델 패턴을 많이 이용하게 될 것 (유지 보수가 대부분 쉽다)

    /*
     * 주문 취소
     * */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다");
        }
        // 그냥 status 만 바꾸었을 뿐, jpa 에게 반영해달라는 말을 안했음.
        // entity manager 를 호출하지도 않았음.
        // 값을 가져와서 바꾸었을 뿐. -> 그냥 jpa 가 "어 값이 바뀌었네?" 하고 transaction commit 시점에 바뀐 애 찾아서
        // db에 update 문 날리고 transaction 을 commit 함.
        // 정확히 말하면 flush 할 때 dirty checking 이 일어남.
        // 이게 기본 메커니즘.
        // 그런데 준 영속 Entity 일 때 문제가 생긴다.
        // 준영속 엔티티 -> persistence context 가 더 이상 관리하지 않는 엔티티를 말함.
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    // == 조회 로직 == //
    /*
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;

        // Stream
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
    }
}
