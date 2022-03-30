package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        // 문제가 생겼을 때 close가 호출이 안됨.
        tx.begin();

        // 정석 코드는 try catch 안에 써야함.
        try {
            // CREATE
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("hello2");
//            em.persist(member);

            // UPDATE
//            Member findMember = em.find(Member.class, 2L);
//            findMember.setName("Leo Hello JPA");
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());


            // DELETE
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("leo");
//            Member member = em.find(Member.class, 1L);
//            System.out.println("member.toString() = " + member.getName());

            Member member = new Member();
            member.setId(10L);
            member.setName("LeoBang");

            // 영속 상태로 들어감 => em안의 영속성 컨텍스트라는데를 통해서 member가 관리된다는 뜻.
            // 이 때 db에 저장되는 것이 아님.
            // Before와 after 사이에 아무것도 없고, 이와 관계 없이 뒤에 insert query가 날라감.
            // 영속 상태와 db저장은 무관함.
            // 언제 transaction을 commit하는 시점에 영속성 컨텍스트 안에 있는 애들이 db에 query로 날라감.
            System.out.println("=====BEFORE=====");
            em.persist(member);
            em.detach(member);

            em.remove(member);
            System.out.println("=====AFTER=====");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }
}
