package am.personal.acc_management.Repo.Account;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.util.DBconnectionJPA;
import am.personal.acc_management.util.Exceptions.InvalidInputException;
import am.personal.acc_management.util.Exceptions.UserExistsException;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

public class accRepoJPA implements accRepo{
    private static SessionFactory sessionFactory;
    public accRepoJPA()
    {
        sessionFactory = DBconnectionJPA.getSessionFactory();
    }

    @Override
    @Transactional
    public void addUser(User user){
        var session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User getUser(int id) {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        User user =session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public User getUserByEmail(String email)  {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        NativeQuery<User> nativeQuery = session.createNativeQuery(
                "SELECT * FROM accounts WHERE email = :email", User.class);
        nativeQuery.setParameter("email", email);
        User user = null;
        try{
            user = nativeQuery.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void updateUser(User user) {
        var session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }
}
