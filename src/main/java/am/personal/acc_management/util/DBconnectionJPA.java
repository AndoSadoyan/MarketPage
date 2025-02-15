package am.personal.acc_management.util;


import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import javax.management.relation.Role;
import java.util.Properties;

public class DBconnectionJPA {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER,"org.postgresql.Driver");
                settings.put(Environment.URL,"jdbc:postgresql://localhost:5432/market");
                settings.put(Environment.USER,"postgres");
                settings.put(Environment.PASS,"Ando1337");
                settings.put(Environment.DIALECT,"org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.SHOW_SQL,"true");
                settings.put(Environment.FORMAT_SQL,"true");
                settings.put(Environment.HBM2DDL_AUTO,"update");

                config.setProperties(settings);
                config.addAnnotatedClass(User.class);
                config.addAnnotatedClass(Product.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();

                sessionFactory = config.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }


}
