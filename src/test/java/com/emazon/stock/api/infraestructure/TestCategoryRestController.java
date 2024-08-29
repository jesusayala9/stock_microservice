package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.handler.ICategoryHandler;
import com.emazon.stock.api.application.utils.CategoryConstraints;
import com.emazon.stock.api.infraestructure.input.rest.CategoryRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
