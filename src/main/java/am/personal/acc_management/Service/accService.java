package am.personal.acc_management.Service;
import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Model.User;
import am.personal.acc_management.Repo.Account.accRepo;
import am.personal.acc_management.util.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class accService {
    accRepo DB ;

    @Autowired
    public accService(accRepo DB)
    {
        this.DB = DB;
    }

    @Transactional
    public void addUser(User user) throws InvalidInputException, UserExistsException {
        validate(user.getEmail(), user.getPassword());
        var u = DB.getUserByEmail(user.getEmail());
        if (u != null)
            throw new UserExistsException("User with this email already exists");
        DB.addUser(user);
    }

    @Transactional
    public User getUser(String email,String password) throws InvalidInputException
    {
        validEmail(email);
        var user=DB.getUserByEmail(email);
        if(user == null || !user.getPassword().equals(password)) {
            throw new InvalidInputException("Invalid email or password");
        }
        return user;
    }
    @Transactional
    public User getUserByEmail(String email)
    {
        return DB.getUserByEmail(email);
    }

    @Transactional
    public void addToCart(User user, Product product) throws InvalidInputException, UserExistsException
    {
        var u = DB.getUserByEmail(user.getEmail());
        var cart = u.getCart();
        if(cart == null)
            cart = new ArrayList<>();
        cart.add(product);
        DB.updateUser(u);
    }

    @Transactional
    public void changePassword(String email,String new1, String new2) throws InvalidInputException {
        if(!validPassword(new1) || !validPassword(new2))
            throw new InvalidInputException("Invalid Password");
        if(!new1.equals(new2))
            throw new InvalidInputException("Passwords do not match");
        var user = DB.getUserByEmail(email);
        user.setPassword(new1);
        DB.updateUser(user);
    }

    private void validate(String email, String password) throws InvalidInputException
    {
        if(!validEmail(email) || !validPassword(password))
            throw new InvalidInputException("Invalid email or password form");
    }

    private static boolean validEmail(String email)
    {
        if(!email.contains("@") || !email.contains(".") || email.charAt(0) == '.')
            return false;
        return email.matches("^[a-zA-Z0-9._%+-]+@+[a-zA-Z0-9.-]{2,}.[a-zA-Z]{2,}$");
    }

    private static boolean validPassword(String password)
    {
        return password.length() >=8;
    }
}
