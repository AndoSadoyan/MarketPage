package am.personal.acc_management.Repo.Product;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.util.Exceptions.*;

import java.util.List;

public interface productRepo {
    public void save(Product product);
    public void remove(Product product);
    public Product getProduct(Long id);
    public Product getProductByName(String productName);
    public List<Product> getAllProducts();
}
