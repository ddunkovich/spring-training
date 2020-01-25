package by.ddunkovich.playground.spring.demo;

public class Order {

    String id;
    String createDate;

    public Order(String id, String createDate) {
        this.id = id;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
