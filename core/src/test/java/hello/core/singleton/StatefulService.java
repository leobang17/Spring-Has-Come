package hello.core.singleton;

public class StatefulService {
//    이딴식으로 stateful한, 상태를 유지하는 property가 존재하면 안된다.
//    공유하는 싱글톤 객체를 다른 유저가 호출할 때 값을 바꾸면, 다른 사람이 공유하는 싱글톤 객체의 값도 바뀌어 버린다.
    private int price; // 상태를 유지하는 필드


    public void order(String name, int price) {
        System.out.println("name = " + name + "Price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }
}
