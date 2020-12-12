package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.ProductRepository;
import by.bntu.fitr.povt.vasilkou.bntu_shop.service.api.CartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    private final Map<Product, Integer> products = new HashMap<>();

    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Map<Product, Integer> getCart() {
        return Collections.unmodifiableMap(products);
    }

    @Override
    public void addCartItem(Product product, int amount) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + amount);
        } else {
            products.put(product, amount);
        }
    }

    @Override
    public void removeCartItem(Product product) {
        products.remove(product);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getCost().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void checkout() throws Exception  {
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            product = productRepository.findById(entry.getKey().getId()).get();
            if (product.getAmount() < entry.getValue())
                throw new Exception("Amount diff");
            entry.getKey().setAmount(product.getAmount() - entry.getValue());
        }
        productRepository.saveAll(products.keySet());
        productRepository.flush();
        products.clear();
    }
}
