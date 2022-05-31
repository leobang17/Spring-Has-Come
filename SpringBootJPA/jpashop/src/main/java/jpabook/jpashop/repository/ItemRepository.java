package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;


    public void save(Item item) {
        if (item.getId() == null) {
            // item은 jpa에 저장하기 전까지 id 값이 없음.
            // id 값이 없다는 것은 완전히 새로 생성한 객체라는 것. -> 신규로 등록해줘야 함.
            em.persist(item);
        } else {
            // db 에 있건 영속성 컨텍스트에만 존재하건 일단 id 값이 있으므로. persist 가 아니라 update 해주어야 함/
            // update 비슷한 것.
            // web app에서 다시 자세히 설명함.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
