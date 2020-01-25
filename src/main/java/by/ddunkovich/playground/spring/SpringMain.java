package by.ddunkovich.playground.spring;

import by.ddunkovich.playground.spring.demo.Order;
import by.ddunkovich.playground.spring.demo.OrderService;
import by.ddunkovich.playground.spring.demo.TimeLimitedScope;
import by.ddunkovich.playground.spring.util.DateUtil;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class SpringMain {
    public static void main(String[] args) {


        GenericApplicationContext ctx = new GenericApplicationContext();
        new XmlBeanDefinitionReader(ctx).loadBeanDefinitions("context.xml");
        ctx.refresh();

        ctx.getBeanFactory().registerScope("timeLimitedScope", new TimeLimitedScope());

        // Create bean each 1 second
        new Thread(() -> {
            while(true){
                OrderService orderService= ctx.getBean(OrderService.class);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

//        Order order = orderService.createRandomOrder();

//        System.out.println(order);
    }
}
