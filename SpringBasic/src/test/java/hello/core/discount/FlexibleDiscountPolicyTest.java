package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FlexibleDiscountPolicyTest {
    DiscountPolicy discountPolicy = new FlexibleDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o() {
        // given
        Member member1 = new Member(1L, "memberVIP", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member1, 10000);

        // then
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    void vip_x() {
        // given
        Member member1 = new Member(1L, "memberBasic", Grade.BASIC);

        // when
        int discount = discountPolicy.discount(member1, 10000);

        // then
        Assertions.assertThat(discount).isEqualTo(0);
    }
}