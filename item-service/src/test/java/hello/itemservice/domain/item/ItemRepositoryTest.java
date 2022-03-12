package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(savedItem);
    }


    @Test
    void findAll() {
        Item itemA = new Item("itemA", 10000, 100);
        Item itemB = new Item("itemB", 10000, 100);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        List<Item> items = itemRepository.findAll();

        Assertions.assertThat(items.size()).isSameAs(2);
        Assertions.assertThat(items).contains(itemA, itemB);
    }

    @Test
    void update() {
        Item itemA = new Item("itemA", 10000, 100);

        itemRepository.save(itemA);

        Item itemB = new Item("itemB", 12000, 120);
        itemB.setId(1L);

        itemRepository.update(itemA.getId(), itemB);

        Item findItem = itemRepository.findById(itemA.getId());
        Assertions.assertThat(findItem.getItemName()).isEqualTo(itemB.getItemName());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(itemB.getQuantity());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(itemB.getPrice());

    }

}