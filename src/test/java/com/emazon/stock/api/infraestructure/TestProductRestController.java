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




import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    void getAllProductsTest() throws Exception {

        ProductResponse productResponse = new ProductResponse(
                "Diadema",
                "Diadema con sonido envolvente",
                5,
                1000.0,
                11L,
                List.of(1L, 2L)
        );

        PagedResult<ProductResponse> pagedProducts = new PagedResult<>(List.of(productResponse), 1, 10);

        when(productHandler.getAllProducts(any(), any())).thenReturn(pagedProducts);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/product/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("direction", "ASC")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value("Diadema"))
                .andExpect(jsonPath("$.content[0].description").value("Diadema con sonido envolvente"))
                .andExpect(jsonPath("$.content[0].quantity").value(5))
                .andExpect(jsonPath("$.content[0].price").value(1000.0))
                .andExpect(jsonPath("$.content[0].brandId").value(11L))
                .andExpect(jsonPath("$.content[0].categoryIds[0]").value(1L))
                .andExpect(jsonPath("$.content[0].categoryIds[1]").value(2L))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(productHandler, times(1)).getAllProducts(any(), any());
    }

    @Test
    void getAllProducts_emptyPageTest() throws Exception {
        // Arrange
        PagedResult<ProductResponse> pagedProducts = new PagedResult<>(List.of(), 0, 10); // Página vacía

        when(productHandler.getAllProducts(any(), any())).thenReturn(pagedProducts);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/product/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("direction", "ASC")
                .accept(MediaType.APPLICATION_JSON));

        // Assert
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(productHandler, times(1)).getAllProducts(any(), any());
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
