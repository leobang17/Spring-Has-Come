package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        // class로 넘어오는 type이랑 Item의 타입이랑 같냐 판단
        //  item == clazz
        //  item = subItem (item의 자식) sub class여도 통과한다.
    }


    /**
     *
     * @param target
     *   target은 Object로 인터페이스에서 딱 박아놨음. -> 쓰려면 검증하려는 class type으로 casting 해줘야 한다.
     * @param errors
     *   bindingResults의 부모 class -> bindingResults 넣어줄 수 있음 (polymorphism)
     */
    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1_000_000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"required.item.price"}, new Object[]{1000, 10_000}, null));
            errors.rejectValue("price", "range", new Object[]{1000, 1_000_000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false ,new String[]{"max.item.quantity"}, new Object[]{9_999}, null));
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10_000) {
//                errors.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10_000, resultPrice}, null));
                errors.reject("totalPriceMin", new Object[]{10_000, resultPrice}, null);
            }
        }
    }
}
