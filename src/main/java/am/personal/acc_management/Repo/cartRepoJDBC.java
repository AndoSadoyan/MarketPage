package am.personal.acc_management.Repo;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Repo.Product.productRepoJDBC;
import am.personal.acc_management.Service.productService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class cartRepoJDBC {
    private final Connection conn;


    public cartRepoJDBC(Connection conn)
    {
        this.conn = conn;
        Statement stmt = null;
        try {
            stmt = this.conn.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS cart ("+
                            "c_id bigserial primary key, "+
                            "acc_email varchar(255) not null "+
                            " constraint fk_carts_accounts "+
                            " references accounts(email), "+
                            "product_name varchar(30) not null "+
                            " constraint fk_carts_products "+
                            " references  products(name))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCart(String acc_email,String product_name)
    {
        try {
            var addStatement = conn.prepareStatement("INSERT INTO cart (acc_email,product_name) values(?,?)");
            addStatement.setString(1, acc_email);
            addStatement.setString(2, product_name);
            addStatement.executeUpdate();
            addStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Product> getProducts(String acc_email)
    {
        List<Product> products = new ArrayList<>();
        try {
            var stmt = conn.prepareStatement("SELECT * FROM cart join products on product_name = name " +
                    "where acc_email=?");
            stmt.setString(1,acc_email);
            var set = stmt.executeQuery();
            while(set.next())
            {
                products.add(new Product(set.getString("name"),
                        set.getInt("price"),1));
            }
            set.close();
            stmt.close();
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void clearCart(String email)
    {
        try {
            var stmt = conn.prepareStatement("Delete from cart Where acc_email = ?");
            stmt.setString(1,email);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getUsersPayAmount(String email)
    {
        try {
            var getProductsStmt = conn.prepareStatement("Select product_name from cart where acc_email = ?");
            getProductsStmt.setString(1,email);
            ResultSet resultSet = getProductsStmt.executeQuery();
            productService productservice = new productService(new productRepoJDBC(conn));
            List<Product> products = productservice.getAll();
            int sum = 0;
            while(resultSet.next())
            {
                for(Product product : products)
                    if(product.getName().equals(resultSet.getString("product_name")))
                    {
                        sum += product.getPrice();
                        break;
                    }
            }
            getProductsStmt.close();
            resultSet.close();
            return sum;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
