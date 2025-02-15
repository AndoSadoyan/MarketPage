package am.personal.acc_management.Repo.Product;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.util.Exceptions.NoProductException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class productRepoJDBC implements productRepo {
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

    @Override
    public List<Product> getAllProducts()
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

    @Override
    public Product getProductByName(String name) {
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

    @Override
    public void save(Product product)
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

    @Override
    public void remove(Product product) {
        try {
            var getStatement = conn.prepareStatement("SELECT amount From products where name = ?");
            getStatement.setString(1,product.getName());
            ResultSet amountSet = getStatement.executeQuery();
            int amount ;
            amount = amountSet.getInt("amount");
            if(amount == 1)
            {
                var deleteStatement = conn.prepareStatement("DELETE FROM products WHERE name = ?");
                deleteStatement.setString(1,product.getName());
                deleteStatement.executeUpdate();
                deleteStatement.close();
            }
            else
            {
                var updateStatement = conn.prepareStatement("UPDATE products Set amount = ? WHERE name = ?");
                updateStatement.setInt(1,amount-1);
                updateStatement.setString(2,product.getName());
                updateStatement.executeUpdate();
                updateStatement.close();
            }
            amountSet.close();
            getStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public Product getProduct(Long id) {
        try {
            var getStatement = conn.prepareStatement("SELECT * From products Where id = ?");
            getStatement.setLong(1, id);
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
}
