package am.personal.acc_management;

import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class myBeans {
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

    public static accService accServiceBean() {
        return context.getBean("accService", accService.class);
    }

    public static productService productServiceBean() {
        return context.getBean("productService", productService.class);
    }
}
