package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // AppConfig의 @Bean을 가져옴.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);


        Member member1 = new Member(1L, "leo", Grade.VIP);
        memberService.join(member1);
        Member res = memberService.findMember(1L);
        System.out.println("newMember = " + member1.getName());
        System.out.println("findMember = " + res.getName());
    }
}
