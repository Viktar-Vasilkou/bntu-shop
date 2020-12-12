package by.bntu.fitr.povt.vasilkou.bntu_shop.service.api;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;

import java.util.List;

public interface CategoryService {
    Category getById(Long id);

    Category save(Category category);

    Category edit(Category category);

    void delete(Category category);

    List<Category> getAll();
}
