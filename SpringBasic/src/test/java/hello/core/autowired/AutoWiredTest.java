package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {
    
    @Test
    void AutoWiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        TestBean bean = ac.getBean(TestBean.class);
        
    }
    
    static class TestBean {
        // Member 는 Spring Bean으로 등록이 되어있지 않기 때문에 Member 빈을 찾으면 에러가 나온다.

        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }
        
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            // 호출은 되는데 null로 들어온다.
            System.out.println("noBean2 = " + noBean2);
        }
        
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            // Optional.empty로 감싸서
            System.out.println("noBean3 = " + noBean3);
        }
    }
    
}
