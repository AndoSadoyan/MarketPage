package am.personal.acc_management;


import am.personal.acc_management.Model.Product;
import am.personal.acc_management.util.DBconnection;

import javax.servlet.http.Cookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        DBconnection db = DBconnection.getDB();
        var conn = db.getConn();
        String acc_email = "aperik1@gmail.com";

        try {
            var stmt = conn.prepareStatement("SELECT * FROM cart join products on product_name = name " +
                    "where acc_email=?");
            stmt.setString(1,acc_email);
            var set = stmt.executeQuery();
            while(set.next())
            {
                System.out.println(set.getString("name"));
                System.out.println(set.getString("price"));
            }
            set.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}