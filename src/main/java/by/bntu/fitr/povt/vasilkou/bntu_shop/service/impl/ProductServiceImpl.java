package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.ProductService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final int AMOUNT_OF_PRODUCTS_ON_PAGE = 15;

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id).orElse(new Product());
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public void delete(Product product) {
        repository.delete(product);
    }

    @Override
    public Product deactivate(Product product) {
        product.setStatus(false);
        return product;
    }

    @Override
    public Product activate(Product product) {
        product.setStatus(true);
        return product;
    }

    @Override
    public Page<Product> getAll(int page) {
        Pageable paging = PageRequest.of(page, AMOUNT_OF_PRODUCTS_ON_PAGE);
        return repository.findAll(paging);
    }

    @Override
    public Page<Product> getAllAvailable(int page, Category category) {
        Pageable paging = PageRequest.of(page, AMOUNT_OF_PRODUCTS_ON_PAGE);

        if (category != null){
            return repository.findAllByCategory(category, paging);
        }

        return repository.findAllAvailable(paging);
    }
}
