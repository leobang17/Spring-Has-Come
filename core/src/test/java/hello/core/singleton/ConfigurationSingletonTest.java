package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository2 = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository = memberService.getMemberRepository();
        MemberRepository memberRepository1 = orderService.getMemberRepository();

        // 세 놈 다 같은 놈이다. 근데 AppConfig를 보면 new를 계속 해주던데 ...
        System.out.println("memberService -> memberRepository = " + memberRepository);
        System.out.println("orderService -> memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);


        Assertions.assertThat(memberRepository).isEqualTo(memberRepository1);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository2);
    }
    
    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
    }
}
