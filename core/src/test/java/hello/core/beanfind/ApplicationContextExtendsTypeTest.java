package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.FlexibleDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextExtendsTypeTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면 오류 가 발생한다.")
    void findBeanbyParenttypeDuplicate () {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회 시 자식이 둘 이상 있으면 빈 이름으로 조회하자 ")
    void findBeanByParentTypeBeanName () {
        DiscountPolicy flexibleDiscountPolicy = ac.getBean("flexibleDiscountPolicy", DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(flexibleDiscountPolicy).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanTypeBySubType() {
        FlexibleDiscountPolicy bean = ac.getBean(FlexibleDiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(FlexibleDiscountPolicy.class);
    }


    @Test
    @DisplayName("부모 타입으로 모두 조회하기 ")
    void findAllBeanByParentType () {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    void findAllBeansByObjectType() {
        // spring이 내부적으로 이용하는 @Bean 까지 모두 딸려서 출력된다. Java 객체의 모든 공통 부모는 Object이므로
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = " + beansOfType.get(key));
        }
    }


    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy flexibleDiscountPolicy() {
            return new FlexibleDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixedDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
