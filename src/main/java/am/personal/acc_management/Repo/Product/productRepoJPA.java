package am.personal.acc_management.Repo.Product;

import am.personal.acc_management.Model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class productRepoJPA implements productRepo {
    SessionFactory sessionFactory;

    public productRepoJPA(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Product product) {
        sessionFactory.getCurrentSession().save(product);
    }

    public void update(Product product)
    {
        sessionFactory.getCurrentSession().update(product);
    }

    @Override
    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }

    @Override
    public Product getProduct(Long id) {
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }

    @Override
    public Product getProductByName(String productName) {
        var session = sessionFactory.getCurrentSession();
        var query = session.createNativeQuery("select * from products where name = :productName", Product.class);
        query.setParameter("productName", productName);
        Product product = null;
        try{
            product = query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts()
    {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        List<Product> products = new ArrayList<>();
        try {
            products= session.createNativeQuery("select * from products", Product.class).getResultList();
        }
        catch (NoResultException e)
        {
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return products;
    }
}
