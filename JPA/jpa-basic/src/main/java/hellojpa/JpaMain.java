package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        // 문제가 생겼을 때 close가 호출이 안됨.
        tx.begin();

        // 정석 코드는 try catch 안에 써야함.
        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("MemberA");
//            member.setTeamId(team.getId());
//            member.setTeam(team);
            em.persist(member);


            Member member1 = em.find(Member.class, member.getId());
            Team team1 = member1.getTeam();
            System.out.println(team1);
            List<Member> members = team.getMembers();
            for (Member member2 : members) {
                System.out.println("member2 = " + member2);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }
}
