package hello.core.scan;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class AutoAppConfig {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(hello.core.AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
