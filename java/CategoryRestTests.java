package com.ofp.listpoi.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ofp.listpoi.controllers.CategoryController;
import com.ofp.listpoi.entities.Category;
import com.ofp.listpoi.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@Import(RestTestsConfiguration.class)
@WebMvcTest(CategoryController.class)
public class CategoryTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryRepository categoryRepository;

    // Default test instance
    private final Category testCategory = new Category("category", "icon");

    // Default list instance
    private final Collection<Category> testCategories = Arrays.asList(
            new Category("name1", "icon1"),
            new Category("name2", "icon2"),
            new Category("name3", "icon3")
    );


    @Test
    @WithAnonymousUser
    public void cannotCreateWhenNotAuthenticated() throws Exception {
        mvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testCategory)))
                .andExpect(status().isForbidden());
    }


    @Test
    public void canCreateCategory() throws Exception {
        // Setup mock repository to return `testCategory` when `save()` is called
        given(categoryRepository.save(Mockito.any(Category.class))).willReturn(testCategory);

        mvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testCategory)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is(testCategory.getName())))
                .andExpect(jsonPath("$.iconName", is(testCategory.getIconName())));
    }


    @Test
    public void cannotGetNonExistingCategory() throws Exception {
        mvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithAnonymousUser
    public void cannotGetWhenNotAuthenticated() throws Exception {
        mvc.perform(get("/api/v1/categories/1")).andExpect(status().isForbidden());
    }


    @Test
    public void canGetCategoryById() throws Exception {
        // Setup mock repository to return `testCategory` when `save()` is called
        given(categoryRepository.findById(Mockito.anyInt())).willReturn(Optional.of(testCategory));

        mvc.perform(get("/api/v1/categories/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is(testCategory.getName())))
                .andExpect(jsonPath("$.iconName", is(testCategory.getIconName())));

        verify(categoryRepository).findById(2);
    }


    @Test
    public void cannotUpdateNonExistingCategory() throws Exception {
        // Setup mock repository to return `false` when `existsById()` is called
        given(categoryRepository.existsById(Mockito.anyInt())).willReturn(false);

        mvc.perform(put("/api/v1/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testCategory)))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithAnonymousUser
    public void cannotUpdateWhenNotAuthenticated() throws Exception {
        mvc.perform(put("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testCategory)))
                .andExpect(status().isForbidden());
    }


    @Test
    public void canUpdateCategory() throws Exception {
        Category updatedCategory = new Category("newName", "newIconName");

        // Setup mock repository to return `true` when `existsById()` is called
        given(categoryRepository.existsById(Mockito.anyInt())).willReturn(true);
        // Setup mock repository to return `updatedCategory` when `save()` is called
        given(categoryRepository.save(Mockito.any(Category.class))).willReturn(updatedCategory);

        mvc.perform(put("/api/v1/categories/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedCategory)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", is("newName")))
                .andExpect(jsonPath("$.iconName", is("newIconName")));

        verify(categoryRepository).existsById(3);
    }


    @Test
    public void cannotDeleteNonExistingCategory() throws Exception {
        // Setup mock repository to return `false` when `existsById()` is called
        given(categoryRepository.existsById(Mockito.anyInt())).willReturn(false);

        mvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithAnonymousUser
    public void cannotDeleteWhenNotAuthenticated() throws Exception {
        mvc.perform(delete("/api/v1/categories/1")) .andExpect(status().isForbidden());
    }


    @Test
    public void canDeleteCategory() throws Exception {
        // Setup mock repository to return `true` when `existsById()` is called
        given(categoryRepository.existsById(Mockito.anyInt())).willReturn(true);

        mvc.perform(delete("/api/v1/categories/4"))
                .andExpect(status().isOk());

        verify(categoryRepository).existsById(4);
        verify(categoryRepository).deleteById(4);
    }


    @Test
    @WithAnonymousUser
    public void cannotListWhenNotAuthenticated() throws Exception {
        mvc.perform(get("/api/v1/categories")).andExpect(status().isForbidden());
    }


    @Test
    public void canListCategories() throws Exception {
        // Setup mock repository to return `testCategories` when `findAll()` is called
        given(categoryRepository.findAll()).willReturn(testCategories);

        mvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[2].name", is("name3")));

        verify(categoryRepository).findAll();
    }


    @Test
    public void canFilterCategories() throws Exception {
        // Setup mock repository to return `testCategories` when `findByNameContaining()` is called
        given(categoryRepository.findAllByNameContaining(Mockito.anyString())).willReturn(testCategories);

        mvc.perform(get("/api/v1/categories")
                .param("filter", "myFilter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[1].name", is("name2")))
                .andExpect(jsonPath("$[2].name", is("name3")));

        verify(categoryRepository).findAllByNameContaining("myFilter");
    }

}