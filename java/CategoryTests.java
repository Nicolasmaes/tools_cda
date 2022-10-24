package com.ofp.listpoi.jpa;

import com.ofp.listpoi.entities.Category;
import com.ofp.listpoi.repositories.CategoryRepository;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    // Default test instance
    private final Category testCategory = new Category("name", "icon");

    // Helper function to initialize some categories in table
    private void persistLists() {
        entityManager.persistAndFlush(new Category("first category", "icon1"));
        entityManager.persistAndFlush(new Category("second category", "icon2"));
        entityManager.persistAndFlush(new Category("third category", "icon3"));
    }

    @Test
    public void canCreateCategory() {
        categoryRepository.save(testCategory);

        Iterable<Category> categories = categoryRepository.findAll();
        assertEquals(1, IterableUtil.sizeOf(categories));
        assertEquals("name", categories.iterator().next().getName());
        assertEquals("icon", categories.iterator().next().getIconName());
    }

    @Test
    public void canGetById() {
        Category category = entityManager.persistAndFlush(testCategory);

        Optional<Category> found = categoryRepository.findById(category.getId());
        assertTrue(found.isPresent());
        assertEquals(category.getId(), found.get().getId());
    }

    @Test
    public void cannotCreateCategoryWithExistingName() {
        entityManager.persistAndFlush(testCategory);
        assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(new Category("name", "newicon")));
    }

    @Test
    public void cannotCreateCategoryWithExistingIcon() {
        entityManager.persistAndFlush(testCategory);
        assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(new Category("newname", "icon")));
    }

    @Test
    public void canUpdate() {
        Category category = entityManager.persistAndFlush(testCategory);

        category.setName("updatedName");
        category.setIconName("updatedIcon");
        categoryRepository.save(category);

        Category found = categoryRepository.findById(category.getId()).orElseThrow();
        assertEquals("updatedName", found.getName());
        assertEquals("updatedIcon", found.getIconName());
    }

    @Test
    public void canDeleteById() {
        Category category = entityManager.persistAndFlush(testCategory);

        categoryRepository.deleteById(category.getId());
        assertTrue(categoryRepository.findById(category.getId()).isEmpty());
    }

    @Test
    public void canList() {
        persistLists();

        ArrayList<Category> list = new ArrayList<>(IterableUtil.toCollection(categoryRepository.findAll()));
        assertEquals(3, list.size());
        assertEquals("first category", list.get(0).getName());
        assertEquals("second category", list.get(1).getName());
        assertEquals("third category", list.get(2).getName());
    }

    @Test
    public void canFilterByName() {
        persistLists();

        ArrayList<Category> list = new ArrayList<>(IterableUtil.toCollection(categoryRepository.findAllByNameContaining("cat")));
        assertEquals(3, list.size());
        assertEquals("first category", list.get(0).getName());
        assertEquals("second category", list.get(1).getName());
        assertEquals("third category", list.get(2).getName());

        list = new ArrayList<>(IterableUtil.toCollection(categoryRepository.findAllByNameContaining("ir")));
        assertEquals(2, list.size());
        assertEquals("first category", list.get(0).getName());
        assertEquals("third category", list.get(1).getName());
    }
}
