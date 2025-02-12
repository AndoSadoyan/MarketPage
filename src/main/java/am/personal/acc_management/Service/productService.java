package am.personal.acc_management.Service;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Repo.productRepoJDBC;
import am.personal.acc_management.util.Exceptions.NoProductException;

import java.util.List;

public class productService {
    productRepoJDBC DB;

    public productService(productRepoJDBC repo)
    {
        DB = repo;
    }

    public Product getProduct(String name)
    {
        return DB.getProduct(name);
    }

    public List<Product> getAll()
    {
        return DB.getAll();
    }

    public void removeProduct(String name) throws NoProductException {
        DB.removeProduct(name);
    }
}
