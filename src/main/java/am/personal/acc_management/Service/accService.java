package am.personal.acc_management.Service;
import am.personal.acc_management.Model.User;
import am.personal.acc_management.Repo.accRepoJDBC;
import am.personal.acc_management.util.Exceptions.*;


public class accService {
    accRepoJDBC DB ;

    public accService(accRepoJDBC DB)
    {
        this.DB = DB;
    }



    public void addUser(User user) throws InvalidInputException, UserExistsException {
        validate(user.getEmail(), user.getPassword());
        DB.addUser(user);
    }

    public User getUser(String email, String password) throws InvalidInputException
    {
        validate(email, password);
        return DB.getUser(email, password);
    }


    public void changePassword(String email,String new1, String new2) throws InvalidInputException {
        if(!validPassword(new1) || !validPassword(new2))
            throw new InvalidInputException("Invalid Password");
        if(!new1.equals(new2))
            throw new InvalidInputException("Passwords do not match");
        var user = DB.getUserByEmail(email);
        user.setPassword(new1);
        DB.updateUser(user);
    }

    public void pay(String email, int amount)
    {

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
