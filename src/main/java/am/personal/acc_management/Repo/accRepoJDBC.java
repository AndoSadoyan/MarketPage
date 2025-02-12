package am.personal.acc_management.Repo;

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

    @Override
    public User getUser(int id) throws InvalidInputException {

        try(var stmt = conn.prepareStatement("Select * From accounts where acc_id=?"))
        {
            stmt.setInt(1, id);
            var ResultSet = stmt.executeQuery();
            if(!ResultSet.next())
                throw new InvalidInputException("Invalid id");
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
    public User getUserByEmail(String email) throws InvalidInputException
    {

        try(var stmt = conn.prepareStatement("Select * From accounts where email=?"))
        {
            stmt.setString(1, email);
            var ResultSet = stmt.executeQuery();
            if(!ResultSet.next())
                throw new InvalidInputException("Invalid email");
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
