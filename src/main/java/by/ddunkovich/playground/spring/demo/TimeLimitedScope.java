package by.ddunkovich.playground.spring.demo;

import by.ddunkovich.playground.spring.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

// ToDo change scope to Singleton, not Thread
public class TimeLimitedScope implements Scope {

    private static final Log logger = LogFactory.getLog(TimeLimitedScope.class);
    private final ThreadLocal<Map<String, Object>> threadScope = new NamedThreadLocal<Map<String, Object>>("TimeLimitedScope") {
        protected Map<String, Object> initialValue() {
            return new HashMap();
        }
    };

    public TimeLimitedScope() {
        System.out.println("Scope have bean created :)");
    }


    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> scope = (Map) this.threadScope.get();
        Object scopedObject = scope.get(name);
        if (scopedObject == null) {
            scopedObject = objectFactory.getObject();
            scope.put(name, scopedObject);
            System.out.println(String.format("%s: Object %s have been created", DateUtil.getCurrentDate(), name));

            // ToDo recreate beans on calling get() if they live more than 8min
            // Remove a bean after 1 minute delay
            new Thread(() -> {
                try {
                    Thread.sleep(5*1000);
                    scope.remove(name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return scopedObject;
    }

    @Nullable
    public Object remove(String name) {
        Map<String, Object> scope = (Map) this.threadScope.get();
        System.out.println("Object " + name + " have been removed");
        return scope.remove(name);
    }

    public void registerDestructionCallback(String name, Runnable callback) {
        //logger.warn("SimpleThreadScope does not support destruction callbacks. Consider using RequestScope in a web environment.");
        System.out.println("Object " + name + " have been removed");
    }

    @Nullable
    public Object resolveContextualObject(String key) {
        return null;
    }

    public String getConversationId() {
        return Thread.currentThread().getName();
    }
}

