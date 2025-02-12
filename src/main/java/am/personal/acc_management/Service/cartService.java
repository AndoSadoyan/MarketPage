package am.personal.acc_management.Service;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Repo.cartRepo;
import am.personal.acc_management.Repo.productRepo;
import am.personal.acc_management.util.DBconnection;
import am.personal.acc_management.util.Exceptions.NoProductException;

import java.util.List;

public class cartService {

    private final cartRepo DB;

    public cartService(cartRepo DB)
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
