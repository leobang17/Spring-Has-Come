package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// 순전한 unit test가 아니라 db까지 도는 것을 보여주기 위해 완전히 spring과 integration 을 해서 실행할 것.
@RunWith(SpringRunner.class)
@SpringBootTest
// 이게 있어야 rollback이 됨
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // insert query 가 안나가는 이유
    // join 은 em.persist 를 하는데, persist 해도 flush가 안되면 db로 쿼리가 안나감
    // 이 flush 는 transaction 이 commit 될 때 자동으로 실행되는데, @Transactional 이 붙은 class의 메서드는 모두 commit 대신 rollback을 해버림
    // 그래서 @Rollback(value = false) 로 주어야 insert 쿼리가 날아가는 것을 볼 수 있음
    // insert query 없이도 객체를 가져오는 이유는 persistence context 에 Member의 id값을 key로, Member 객체를 value로 mapping 되어있기 때문.
    @Test
//    @Rollback(value = false)
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

//    @org.junit.Test(expected = IllegalStateException.class)
    @Test
    public void duplicationException() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
//        memberService.join(member2); // 예외가 발생해야한다 !!!

        // then
//        fail("예외가 발생해야합니다.");
    }

}
