package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// jpa의 작업들은 다 transaction 단위로 실행되는게 좋음 -> 그래야 lazy loading 같은게 동작한다.
// class level 에서 @Transactional 을 걸어주게 되면, 안의 메서드들은 다 transaction 이 걸려들어가게 됨.
@RequiredArgsConstructor
// final 키워드가 걸려있는 required argument들에 대해서 constructor 를 자동으로 만들어준다.
public class MemberService {
    // Bean 을 조회해서 알아서 inject 해줌 (field injection)
    // field injection 의 단점
    // 1. test할 때 어려움 (주입하는 걸 못바꾸므로)
//    @Autowired
    private final MemberRepository memberRepository;

    // Setter injection
    // 장점 -> Test코드를 작성할 때 임의로 주입해줄 수 있음
    // 단점 -> 런타임에 누군가가 저 setter를 통해서 바꿀 수 있음.
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // Constructor Injection
    // 이게 좋음. tc 작성할 떄도 new 로 직접 주입해줄 수 있음. (의존성을 명확하게 알수도 있음)
    // spring 최신 버전에서는 constructor 가 1개일 경우 자동으로 autowire 해주므로 @Autowired가 필요 없음
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional // 얘는 readOnly 가 false 여야 insert를 해주므로
    public Long join(Member member) {
        validateDupliateMember(member); // 중복 회원 검증 로징
        // repository에서 em.persist 해줌.
        // persist하는 순간 insert query는 안날려도 persistence context 에 key-value 형태로 들어가게 됨.
        // -> 따라서 항상 member.getId()의 경우 id 값은 가지고 있게 된다
        // 회원 도메인 개발 - 회원 서비스 개발 - 4:03
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 전제 조회
    // readOnly 를 true 로 줄 경우 jpa 에서 select 를 하는 작업에서는 더 최적화됨.
//    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 한 명만 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


    // 중복 회원 검증해주는 logic
    private void validateDupliateMember(Member member) {
        // 두 명의 user가 동시에 같은 id 로 validate 를 한다면?
        // 이 validation은 둘 다 통과할 것이고 save 가 두 개가 될 것 -> 문제!!!!
        // 실무에서는 최후의 방어를 해야함. (멀티 스레드 환경을 고려해서 db 의 username 필드를 unique 제약조건을 걸어주어야 한다.)
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
