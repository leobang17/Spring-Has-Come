package jpabook.jpashop.service;

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
}
