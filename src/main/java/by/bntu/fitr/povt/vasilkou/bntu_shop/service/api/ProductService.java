package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product getById(Long id);
    Product save(Product product);
    Product edit(Product product);
    void delete(Product product);

    Product deactivate(Product product);
    Product activate(Product product);

    Page<Product> getAll(int page);

    Page<Product> getAllAvailable(int page, Category category);

    List<Product> getProductContains(String string);
}
