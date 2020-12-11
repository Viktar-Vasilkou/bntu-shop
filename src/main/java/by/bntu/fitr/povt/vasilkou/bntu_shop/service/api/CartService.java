package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {
    Map<Product, Integer> getCart();
    void addCartItem(Product product, int amount);
    void removeCartItem(Product product);
    BigDecimal getTotalPrice();
    void checkout() throws Exception;
}
