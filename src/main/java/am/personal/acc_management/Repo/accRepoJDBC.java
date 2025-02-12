package am.personal.acc_management.Repo;

import am.personal.acc_management.Model.User;
import am.personal.acc_management.util.Exceptions.InvalidInputException;
import am.personal.acc_management.util.Exceptions.UserExistsException;

import java.sql.*;

public class accRepoJDBC {
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
                            "balance double precision not null )");
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) throws UserExistsException {
        String email=user.getEmail();
        String password=user.getPassword();
        String username=user.getUsername();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("Select exists( Select 1 from accounts where email = ?)");
            preparedStatement.setString(1, email);
            ResultSet Set = preparedStatement.executeQuery();
            if(Set.next() && Set.getBoolean(1))
                throw new UserExistsException("User with that email already exists");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public User getUser(String email, String password) throws InvalidInputException {
        try(PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT * from accounts Where email = ? and password = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            var Set = preparedStatement.executeQuery();

            if(!Set.next())
                throw new InvalidInputException("Invalid email or password");

            return new User(Set.getString("email"),
                    Set.getString("username"),
                    Set.getString("password"),
                    Set.getInt("balance"));
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void changePassword(String email, String newPassword){

        try {
            var stmt = conn.prepareStatement("UPDATE accounts SET password=? where email=?");
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBalance(String email, int newBalance)
    {
        try {
            var stmt = conn.prepareStatement("UPDATE accounts SET balance = ? where email = ?");
            stmt.setInt(1, newBalance);
            stmt.setString(2, email);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
