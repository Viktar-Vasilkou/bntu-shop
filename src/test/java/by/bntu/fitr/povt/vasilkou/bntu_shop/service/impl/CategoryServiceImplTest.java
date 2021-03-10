package by.bntu.fitr.povt.vasilkou.bntu_shop.service.impl;

import by.bntu.fitr.povt.vasilkou.bntu_shop.model.Category;
import by.bntu.fitr.povt.vasilkou.bntu_shop.repositories.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private static final List<Category> categories = List.of(
            Category.builder().id(1L).build(),
            Category.builder().id(2L).build()
    );

    @Test
    public void testGetById_tryToGetCategoryById_category() {
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.of(categories.get(0)));

        Category expected = Category.builder().id(id).build();
        Category actual = categoryService.getById(id);

        assertEquals(expected, actual);

        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void testGetById_tryToGetCategoryById_newCategory() {
        Long id = 10L;

        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category()));

        Category expected = new Category();
        Category actual = categoryService.getById(id);

        assertEquals(expected, actual);

        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    public void testSave_tryToSaveNewCategory_Saved() {
        when(categoryRepository.save(new Category())).thenReturn(Category.builder().id(1L).build());

        Category expected = Category.builder().id(1L).build();
        Category actual = categoryService.save(new Category());

        assertEquals(expected, actual);

        verify(categoryRepository, times(1)).save(new Category());
    }

    @Test
    public void testDelete_tryToDeleteCategory_Deleted() {
        categoryService.delete(new Category());
        verify(categoryRepository, times(1)).delete(new Category());
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void testGetAll_tryToGetAll_All() {
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> actual = categoryService.getAll();

        assertEquals(categories, actual);

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }
}