package am.personal.acc_management.Service;

import am.personal.acc_management.Model.Product;
import am.personal.acc_management.Repo.Product.productRepo;
import am.personal.acc_management.util.Exceptions.NoProductException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class productService {
    productRepo DB;

    public productService(productRepo repo)
    {
        DB = repo;
    }

    @Transactional
    public void addProduct(Product product)
    {
        Product pr = DB.getProductByName(product.getName());
        if(pr==null)
            DB.save(product);
        else
        {
            pr.setAmount(pr.getAmount()+1);
            DB.update(pr);
        }
    }

    @Transactional
    public Product getProduct(String name) {
        return DB.getProductByName(name);
    }

    @Transactional
    public List<Product> getAll()
    {
        return DB.getAllProducts();
    }

    @Transactional
    public void removeProduct(String name) throws NoProductException {
        Product product = DB.getProductByName(name);
        product.setAmount(Math.min(product.getAmount()-1,0));
        DB.update(product);
    }
}
