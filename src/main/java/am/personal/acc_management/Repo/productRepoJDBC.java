package am.personal.acc_management.Repo;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.util.Exceptions.NoProductException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class productRepoJDBC {
    private final Connection conn;


    public productRepoJDBC(Connection conn)
    {
        this.conn = conn;
        Statement stmt= null;
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS products ("+
                            "p_id bigserial primary key, "+
                            "name varchar(30) not null unique , "+
                            "price integer not null, " +
                            "amount integer not null )"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAll()
    {
        List<Product> products = new ArrayList<Product>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM products");
            ResultSet set = preparedStatement.executeQuery();
            while (set.next())
            {
                products.add(new Product(set.getString("name"),
                        set.getInt("price"),
                        set.getInt("amount")));
            }
            preparedStatement.close();
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Product getProduct(String name) {
        try {
            var getStatement = conn.prepareStatement("SELECT * From products Where name = ?");
            getStatement.setString(1, name);
            ResultSet rs = getStatement.executeQuery();
            if(rs.next())
            {
                return new Product(rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("amount"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product)
    {
        try {
            PreparedStatement findStatement = conn.prepareStatement("SELECT * FROM products Where name = ?");
            findStatement.setString(1,product.getName());
            ResultSet SelectSet = findStatement.executeQuery();
            if(SelectSet.next())
            {
                var updateStatement = conn.prepareStatement("UPDATE products SET amount = ? WHERE name = ?");
                updateStatement.setInt(1,SelectSet.getInt("amount")+1);
                updateStatement.setString(2,product.getName());
                updateStatement.executeUpdate();
                updateStatement.close();
            }
            else
            {
                var addStatement = conn.prepareStatement("INSERT INTO products (name,price,amount) VALUES (?,?,1)");
                addStatement.setString(1,product.getName());
                addStatement.setInt(2,product.getPrice());
                addStatement.executeUpdate();
                addStatement.close();
            }
            findStatement.close();
            SelectSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProduct(String name) throws NoProductException {
        try {
            var getStatement = conn.prepareStatement("SELECT amount From products where name = ?");
            getStatement.setString(1,name);
            ResultSet amountSet = getStatement.executeQuery();
            int amount = 0;
            if(amountSet.next())
                amount = amountSet.getInt("amount");
            if(amount == 0)
                throw new NoProductException("No "+name+" left😔");
            var updateStatement = conn.prepareStatement("UPDATE products Set amount = ? WHERE name = ?");
            updateStatement.setInt(1,amount-1);
            updateStatement.setString(2,name);
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
