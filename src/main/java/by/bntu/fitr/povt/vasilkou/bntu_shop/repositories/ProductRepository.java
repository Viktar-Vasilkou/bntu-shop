package by.bntu.fitr.povt.vasilkou.bntu_shop.repositories;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    Page<Product> findAll(Pageable pageable);

    @Query("select p from Product p where p.amount > 0 and p.status = true order by p.id")
    Page<Product> findAllAvailable(Pageable pageable);

    @Query("select p from Product p where p.category = :category and p.status = true")
    Page<Product> findAllByCategory(@Param("category")Category category, Pageable pageable);

    List<Product> findByNameContaining(String partOfName);
}
