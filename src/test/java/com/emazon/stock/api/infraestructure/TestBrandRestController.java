package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.application.dto.BrandRequest;
import com.emazon.stock.api.application.dto.BrandResponse;
import com.emazon.stock.api.application.handler.IBrandHandler;
import com.emazon.stock.api.application.utils.BrandConstraints;
import com.emazon.stock.api.domain.exception.PageException;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.infraestructure.input.rest.BrandRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandRestController.class)
 class TestBrandRestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBrandHandler brandHandler;

    @Test
    void testSaveBrand() throws Exception {
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("mazda");
        brandRequest.setDescription("carros");
        mockMvc.perform(post("/brand/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated());
        verify(brandHandler, times(1)).saveBrand(any(BrandRequest.class));
    }

    @Test
    void testSaveBrandReturnBadRequest() throws Exception {
        BrandRequest invalidBrandRequest = new BrandRequest(
                "",
                "Una descripción muy larga que supera el límite de longitud definido en BrandConstraints. La longitud máxima permitida es de 150 caracteres 123455646465656565656666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666."
        );
        mockMvc.perform(post("/brand/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBrandRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains(BrandConstraints.EMPTY_NAME_MESSAGE));
                    assertTrue(responseContent.contains(BrandConstraints.LONG_DESCRIPTION_MESSAGE));
                });
    }

    @Test
    void getAllBrandsWhenExist() throws Exception {
        List<BrandResponse> brands = List.of(
                new BrandResponse(1L,"mazda", "carros"),
                new BrandResponse(2L,"yamaha", "motos")
        );
        PagedResult<BrandResponse> pagedResult = new PagedResult<>(brands, brands.size(), 10);
        when(brandHandler.getAllBrands(0, 10, "name", "ASC")).thenReturn(pagedResult);
        ResultActions resultActions = this.mockMvc.perform(get("/brand/all")
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
                .andExpect(jsonPath("$.content[0].name", is("mazda")))
                .andExpect(jsonPath("$.content[1].name", is("yamaha")));
        verify(brandHandler, times(1)).getAllBrands(0, 10, "name", "ASC");
    }

    @Test
    void getAllBrandsWhenNotExist() throws Exception {
        when(brandHandler.getAllBrands(0, 10, "name", "ASC"))
                .thenThrow(new PageException("Marcas"));
        ResultActions resultActions = this.mockMvc.perform(get("/brand/all")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseContent = result.getResponse().getContentAsString();
                    assertTrue(responseContent.contains("Marcas"));
                });
        verify(brandHandler, times(1)).getAllBrands(0, 10, "name", "ASC");
    }
}
