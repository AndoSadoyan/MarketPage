package am.personal.acc_management.Repo.Account;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.util.Exceptions.InvalidInputException;
import am.personal.acc_management.util.Exceptions.UserExistsException;

public interface accRepo {

    public void addUser(User user);
    public User getUser(int id) ;
    public User getUserByEmail(String email);
    public void updateUser(User user);

}
