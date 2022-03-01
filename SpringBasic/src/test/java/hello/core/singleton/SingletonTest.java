package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 Dependency Injection 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 조회 1.
        MemberService memberService = appConfig.memberService();

        // 조회 2.
        MemberService memberService1 = appConfig.memberService();

        // 참조 값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService);
        System.out.println("memberService1 = " + memberService1);

        Assertions.assertThat(memberService).isNotSameAs(memberService1);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void SingletonSerivceTest() {
        SingletonService instance = SingletonService.getInstance();
        SingletonService instance1 = SingletonService.getInstance();

        System.out.println("instance = " + instance);
        System.out.println("instance1 = " + instance1);

        Assertions.assertThat(instance).isSameAs(instance1);
        // same => instance가 동일한지
        // equal => reference 값이 완전히 동일한지
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService bean = ac.getBean("memberService", MemberService.class);
        MemberService bean1 = ac.getBean("memberService", MemberService.class);

        System.out.println("bean = " + bean);
        System.out.println("bean1 = " + bean1);

        Assertions.assertThat(bean).isSameAs(bean1);
    }
}
