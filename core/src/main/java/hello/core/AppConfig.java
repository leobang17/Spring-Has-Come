package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// Application의 실제 동작에 필요한 "구현체를 생성한다"
// 생성자 주입
// 생성한 객체 instance의 reference를 "생성자를 통해서 주입(연결)" 해준다.
public class AppConfig {

    // 어딘가에서 memberService 객체를 불러다 쓸텐데, 그 때
    public MemberService memberService() {
        // memberService의 구현체를 return해주는데, 여기에 구현체를 넣어줌.
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }


}
