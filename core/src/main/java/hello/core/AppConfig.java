package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FlexibleDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Application의 실제 동작에 필요한 "구현체를 생성한다"
// 생성자 주입
// 생성한 객체 instance의 reference를 "생성자를 통해서 주입(연결)" 해준다.
@Configuration
public class AppConfig {

    // @Bean memberService 호출할 때 new 생성자를 호출하잖아 !

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        System.out.println("call AppConfig.discountPolicy");
        return new FlexibleDiscountPolicy();
    }

    // 어딘가에서 memberService 객체를 불러다 쓸텐데, 그 때
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        // memberService의 구현체를 return해주는데, 여기에 구현체를 넣어줌.
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }


}

//
//public class AppConfig {
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
//
//    public DiscountPolicy discountPolicy() {
//        return new FlexibleDiscountPolicy();
//    }
//
//    // 어딘가에서 memberService 객체를 불러다 쓸텐데, 그 때
//    public MemberService memberService() {
//        // memberService의 구현체를 return해주는데, 여기에 구현체를 넣어줌.
//        return new MemberServiceImpl(memberRepository());
//    }
//
//    public OrderService orderService() {
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
//    }
//}
