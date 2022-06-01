package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    // readonly를 false로 해주어야 하므로 (default가 false)
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 이렇게 위임만 하는 메서드들이 꼭 필요한 것인가??
    // 영한이형은 그냥 controller에서 바로 repository 접근해서 이용하는 것도 큰 문제가 없다고 생각한다고 한다.
    // 솔직히 불필요한 관계가 하나 늘어나는 것이므로.

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // ********
    // 준영속 엔티티 - dirty checking 방식으로 update
    // 이게 merge 보다 더 나은 방법.
    // ********
    @Transactional
    public void updateItem(Long itemId, int price, String name, int stockQuantity) {
        // id 를 기반으로 일단 찾아옴 -> jpa 가 관리하는 persistence context 에 올라가게 되었음.
        Item findItem = itemRepository.findOne(itemId);
        // 그리고 변경 시에도 이렇게 단발성으로 setter를 남발해서는 안된다.
        // change ~~ 같이 무언가 의미있는 메서드를 만들어서 이용하도록 하자.
//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());
//        findItem.setCategories(param.getCategories());
//        findItem.setStockQuantity(param.getStockQuantity());
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        // 이러고 아무것도 호출해줄 필요 없다.
        // 왜냐하면, 일단 findOne 을 통해서 영속성 context 에 엔티티가 올라왔고, 관리되는 상태임.
        // 그런데 아직 transaction 이 끝나기 전에 (commit - flush 되기 전에) 엔티티에 변경사항이 생김
        // ==> persistence context 8가 dirty checking 을 통해 변경사항을 감지하고, update query 를 예약해놓음
        // transaction 이 끝나는 시점에 flush 되면서 update query 가 나가게 되고, db에 변경사항이 반영된다.
    }

    // *******
    // 준영속 엔티티 - merge 방석으로 update
    // *******
    @Transactional
    public void updateItemMerge(Book param) {

    }
}
