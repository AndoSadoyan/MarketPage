package am.personal.acc_management.Repo.Product;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.util.DBconnectionJPA;
import am.personal.acc_management.util.Exceptions.NoProductException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class productRepoJPA implements productRepo {

    SessionFactory sessionFactory;
    public productRepoJPA()
    {
        sessionFactory = DBconnectionJPA.getSessionFactory();
    }
    @Override
    public void save(Product product) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var pr=getProductByName(product.getName());
        if(pr==null)
        {
            session.save(product);
        }
        else
        {
            pr.setAmount(pr.getAmount()+1);
            session.update(pr);
        }
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public void remove(Product product)  {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        var pr =getProductByName(product.getName());
        if(pr.getAmount()==1) {
            session.delete(product);
        }
        else {
            pr.setAmount(pr.getAmount() - 1);
            session.update(pr);
        }
        session.getTransaction().commit();
        session.close();
    }
    @Override
    public Product getProduct(Long id) {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        Product product =session.get(Product.class, id);
        session.getTransaction().commit();
        session.close();
        return product;
    }
    @Override
    public Product getProductByName(String productName) {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        var query = session.createNativeQuery("select * from products where name = :productName", Product.class);
        query.setParameter("productName", productName);
        Product product = null;
        try{
            product = query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
        session.getTransaction().commit();
        session.close();
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
