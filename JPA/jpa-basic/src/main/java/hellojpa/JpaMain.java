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
            // 아직 비영속
            Member member1 = new Member(200L, "aa");
            Member member2 = new Member(160L, "bb");


            // 영속 상태로 들어감 => em안의 영속성 컨텍스트라는데를 통해서 member가 관리된다는 뜻.
            // 이 때 db에 저장되는 것이 아님.
            // Before와 after 사이에 아무것도 없고, 이와 관계 없이 뒤에 insert query가 날라감.
            // 영속 상태와 db저장은 무관함.
            // 언제 transaction을 commit하는 시점에 영속성 컨텍스트 안에 있는 애들이 db에 query로 날라감.
            System.out.println("=====BEFORE=====");
            em.persist(member1);
//            em.persist(member2);
            System.out.println("=====AFTER=====");

            em.flush();

            System.out.println("============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }
}
