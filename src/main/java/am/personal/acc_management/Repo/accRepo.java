package am.personal.acc_management.Repo;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.util.Exceptions.InvalidInputException;
import am.personal.acc_management.util.Exceptions.UserExistsException;

import java.sql.*;

public interface accRepo {

    public void addUser(User user) throws UserExistsException;
    public User getUser(int id) throws InvalidInputException;
    public User getUserByEmail(String email) throws InvalidInputException;
    public void updateUser(User user) throws UserExistsException;

}
