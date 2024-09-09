package com.emazon.stock.api.infraestructure;

import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.handler.IProductHandler;
import com.emazon.stock.api.infraestructure.input.rest.ProductRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestProductRestController.class)
@Import(TestProductRestController.TestConfig.class)
class TestProductRestController {

    private MockMvc mockMvc;

    private final IProductHandler productHandler = mock(IProductHandler.class);

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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

        ResultActions resultActions = this.mockMvc.perform(post("/product/save")
                .content(contentStr)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated());
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
