package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {
    // 원래는 persistenceContext 어노테이션으로 주입해주어야 하는데, jpa와 spring을 같이 이용하면 @Autowired 어노테이션을 이용할 수 있다.
    // == MemberService 에서처럼 em 필드를 final로 바꾸어주고, @RequiredArgsConstructor 로 자동주입할 수 있다는 것.
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}

