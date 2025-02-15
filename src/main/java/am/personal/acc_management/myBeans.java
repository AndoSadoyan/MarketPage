package am.personal.acc_management;

import am.personal.acc_management.Service.accService;
import am.personal.acc_management.Service.productService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public interface myBeans {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Application-context.xml");
    accService accServiceBean = context.getBean("accServiceBean", accService.class);
    productService productServiceBean = context.getBean("productServiceBean", productService.class);
}
