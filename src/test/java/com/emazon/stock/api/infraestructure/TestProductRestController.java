package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.application.handler.IProductHandler;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;


import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class TestProductRestController{

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private IProductHandler productHandler;

    @Test
    void saveProductTest() throws Exception {
        // Arrange
        ProductRequest productRequest = new ProductRequest(
                "Diadema",
                "Diadema con sonido envolvente",
                5,
                1000.0,
                11L,
                List.of(1L, 2L, 3L)
        );

        doNothing().when(productHandler).saveProduct(any(ProductRequest.class));

        String contentStr = new ObjectMapper().writeValueAsString(productRequest);

        // Act
        ResultActions resultActions = mockMvc.perform(post("/product/save")
                .content(contentStr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isCreated());
    }

    @Test
    void getAllProductsWhenExist() throws Exception {
        List<ProductResponse> products = List.of(
                new ProductResponse("Producto 1", "Descripción del producto 1", 10, 100.0, 1L, List.of(1L, 2L)),
                new ProductResponse("Producto 2", "Descripción del producto 2", 20, 200.0, 2L, List.of(2L, 3L))
        );

        int totalElements = 2;
        int pageSize = 10;
        PagedResult<ProductResponse> pagedResult = new PagedResult<>(products, totalElements, pageSize);

        when(productHandler.getAllProducts(0, 10, "name", "ASC", null, null, null)).thenReturn(pagedResult);

        ResultActions resultActions = this.mockMvc.perform(get("/product/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(totalElements)))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Producto 1")))
                .andExpect(jsonPath("$.content[1].name", is("Producto 2")));

        verify(productHandler, times(1)).getAllProducts(0, 10, "name", "ASC", null, null, null);
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
