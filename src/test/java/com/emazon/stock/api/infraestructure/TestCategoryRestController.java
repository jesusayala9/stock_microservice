package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.dto.CategoryResponse;

import com.emazon.stock.api.application.handler.ICategoryHandler;
import com.emazon.stock.api.application.handler.IProductHandler;
import com.emazon.stock.api.application.utils.CategoryConstraints;
import com.emazon.stock.api.domain.exception.PageException;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.infraestructure.input.rest.CategoryRestController;
import com.emazon.stock.api.infraestructure.input.rest.ProductRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;



import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryRestController.class)
class TestCategoryRestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Test
    void testSaveCategory() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Electronics");
        categoryRequest.setDescription("All electronic items");
        mockMvc.perform(post("/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated());
        verify(categoryHandler, times(1)).saveCategory(any(CategoryRequest.class));
    }

    @Test
    void testSaveCategoryReturnBadRequest() throws Exception {
        CategoryRequest invalidCategoryRequest = new CategoryRequest(
                "",
                "Una descripción muy larga que supera el límite de longitud definido en CategoryConstraints. La longitud máxima permitida es de 90 caracteres."
        );
        mockMvc.perform(post("/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCategoryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains(CategoryConstraints.EMPTY_NAME_MESSAGE));
                    assertTrue(responseContent.contains(CategoryConstraints.LONG_DESCRIPTION_MESSAGE));
                });
    }

    @Test
    void getAllCategoriesWhenExist() throws Exception {
        List<CategoryResponse> categories = List.of(
                new CategoryResponse(1L,"Electronics", "All electronic items"),
                new CategoryResponse(2L,"Books", "All kinds of books")
        );
        PagedResult<CategoryResponse> pagedResult = new PagedResult<>(categories, categories.size(), 10);
        when(categoryHandler.getAllCategories(0, 10, "name", "ASC")).thenReturn(pagedResult);
                ResultActions resultActions = this.mockMvc.perform(get("/category/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Electronics")))
                .andExpect(jsonPath("$.content[1].name", is("Books")));
        verify(categoryHandler, times(1)).getAllCategories(0, 10, "name", "ASC");
    }

    @Test
    void getAllCategoriesWhenNotExist() throws Exception {
        when(categoryHandler.getAllCategories(0, 10, "name", "ASC"))
                .thenThrow(new PageException("Categorias"));
        ResultActions resultActions = this.mockMvc.perform(get("/category/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("Categorias"));
                });
        verify(categoryHandler, times(1)).getAllCategories(0, 10, "name", "ASC");
    }



        @TestConfiguration
        static class TestConfig {

            @Bean
            public IProductHandler productHandler() {
                return mock(IProductHandler.class);
            }

            @Bean
            public ProductRestController productRestController(IProductHandler productHandler) {
                return new ProductRestController(productHandler);
            }
        }
    }

