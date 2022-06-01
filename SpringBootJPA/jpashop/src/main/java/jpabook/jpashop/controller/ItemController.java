package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    // item edit 페이지로 보내주는 controller
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        // Book 을 update 하는데 이 BookForm 이란 것을 보낼 것임.
        // Entity 를 보내는 게 아니라.
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        // update 하는 화면에 form 을 보내줌.
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    // Post Request (Item Edit page 에서 날아오는 post)
    @PostMapping("items/{itemId}/edit")
    public String updateItem(
            // 이 path variable 은 안쓸 것. 왜나하면 form 으로 넘어온 것들 안에 id 값이 들어있으니까.
            // @PathVariable("itemId") Long itemId,
            @ModelAttribute("form") BookForm form
            // Model model
    ) {
        // ****
        // controller layer 에서 어설프게 entity 를 생성해 넘기는 일은 ㄴㄴ
//        Book book = new Book();
        // 이렇게 id 를 넘기는 과정은 조심해야함.
        // form 에 이상한 id를 넣고 넘기는 경우도 있기 때문.
        // service layer 에서 해당 item id 에 권한이 있는 user 인지 검증하는 과정이 한 번 있어야 함.
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());

//        itemService.saveItem(book);
        // ****

        itemService.updateItem(form.getId(), form.getPrice(), form.getName(), form.getStockQuantity());

        return "redirect:/items";
    }
}
