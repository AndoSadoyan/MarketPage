package am.personal.acc_management.Service;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Repo.Product.productRepo;
import am.personal.acc_management.util.Exceptions.NoProductException;

import java.util.List;

public class productService {
    productRepo DB;

    public productService(productRepo repo)
    {
        DB = repo;
    }

    public void addProduct(Product product)
    {
        DB.save(product);
    }

    public Product getProduct(String name) throws NoProductException {
        Product product = DB.getProductByName(name);
        if(product == null)
            throw new NoProductException("No such product");
        return product;
    }

    public List<Product> getAll()
    {
        return DB.getAllProducts();
    }

    public void removeProduct(String name) throws NoProductException {
        var product = DB.getProductByName(name);
        if(product == null)
            throw new NoProductException("No such product");
        DB.remove(product);
    }
}
