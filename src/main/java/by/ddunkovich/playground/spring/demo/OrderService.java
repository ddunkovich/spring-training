package by.ddunkovich.playground.spring.demo;

import by.ddunkovich.playground.spring.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class OrderService {

    public OrderService() {
    }

    public Order createRandomOrder(){
        String id = UUID.randomUUID().toString();
        String date = DateUtil.getCurrentDate();
        return new Order(id, date);
    }

    public Order createOrder(){
        return null;
    }






}
