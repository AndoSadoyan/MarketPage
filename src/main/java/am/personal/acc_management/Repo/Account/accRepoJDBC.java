package am.personal.acc_management.Repo.Account;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.util.Exceptions.InvalidInputException;
import am.personal.acc_management.util.Exceptions.UserExistsException;

import java.sql.*;

public class accRepoJDBC implements accRepo{
    private final Connection conn;


    public accRepoJDBC(Connection conn)
    {
        this.conn=conn;

        try {
            Statement stmt= this.conn.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS accounts (" +
                            "acc_id bigserial PRIMARY KEY ," +
                            "email varchar(255) not null unique ,"+
                            "username varchar(255) not null ," +
                            "password varchar(40) not null ,"+
                            "balance double precision not null default 0 )");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) {
        String email=user.getEmail();
        String password=user.getPassword();
        String username=user.getUsername();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO accounts (email, username, password, balance) VALUES (?,?,?,0)");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int id) {

        try(var stmt = conn.prepareStatement("Select * From accounts where id=?"))
        {
            stmt.setInt(1, id);
            var ResultSet = stmt.executeQuery();
            if(!ResultSet.next())
                return null;
            return new User(ResultSet.getString("email"),
                    ResultSet.getString("username"),
                    ResultSet.getString("password"),
                    ResultSet.getInt("balance"));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public User getUserByEmail(String email)
    {

        try(var stmt = conn.prepareStatement("Select * From accounts where email=?"))
        {
            stmt.setString(1, email);
            var ResultSet = stmt.executeQuery();
            if(!ResultSet.next())
                return null;
            return new User(ResultSet.getString("email"),
                    ResultSet.getString("username"),
                    ResultSet.getString("password"),
                    ResultSet.getInt("balance"));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User user) {

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET username=?," +
                " password=?, balance=? where email =?")) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getBalance());
            stmt.setString(4, user.getEmail());
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

}
