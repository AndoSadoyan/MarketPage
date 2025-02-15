package am.personal.acc_management.Repo.Account;

import am.personal.acc_management.Model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.NoResultException;

public class accRepoJPA implements accRepo{
    private final SessionFactory sessionFactory;
    public accRepoJPA(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user){
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getUser(int id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User getUserByEmail(String email)  {
        var session = sessionFactory.getCurrentSession();
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
        sessionFactory.getCurrentSession().update(user);
    }
}
