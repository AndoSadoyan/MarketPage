package am.personal.acc_management.Service;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Repo.cartRepoJDBC;

import java.util.List;

public class cartService {

    private final cartRepoJDBC DB;

    public cartService(cartRepoJDBC DB)
    {
        this.DB = DB;
    }

    public void addToCart(String email, String product_name) {
        DB.addToCart(email,product_name);
    }

    public List<Product> getProducts(String email)
    {
        return DB.getProducts(email);
    }

    public void buy(String email)
    {
        int sum= DB.getUsersPayAmount(email);
    }
}
