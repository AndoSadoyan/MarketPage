package am.personal.acc_management.Repo.Account;

import am.personal.acc_management.Model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository
public class accRepoJPA implements accRepo{
    private SessionFactory sessionFactory;
    @Autowired
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
        return user;
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().merge(user);
    }
}
