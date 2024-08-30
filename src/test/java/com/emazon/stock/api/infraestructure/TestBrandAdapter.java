package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import com.emazon.stock.api.domain.utils.pagination.Pagination;
import com.emazon.stock.api.domain.utils.pagination.SortCriteria;
import com.emazon.stock.api.domain.utils.pagination.SortDirection;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

class TestBrandAdapter {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBrandSuccessfully() {
        Brand brand = new Brand(1L, "mazda", "carros");
        BrandEntity brandEntity = new BrandEntity(1L, "mazda", "carros");
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        brandJpaAdapter.saveBrand(brand);
        verify(brandRepository).save(brandEntity);
    }

    @Test
    void TestIfBrandExists() {
        String brandName = "mazda";
        when(brandRepository.existsByName(brandName)).thenReturn(true);
        boolean exists = brandJpaAdapter.existsByName(brandName);
        verify(brandRepository).existsByName(brandName);
        assertTrue("true", exists);
    }

    @Test
    void TestIfCategoryNotExists() {
        String brandName = "mazda";
        when(brandRepository.existsByName(brandName)).thenReturn(false);
        boolean exists = brandJpaAdapter.existsByName(brandName);
        verify(brandRepository).existsByName(brandName);
        assertFalse("false", exists);
    }

    @Test
    void getAllBrandsWhenExist() {
        Pagination pagination = new Pagination(0, 1); // Page size 1
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        BrandEntity brandEntity = new BrandEntity(1L, "mazda", "carros");
        List<BrandEntity> brandEntityList = List.of(brandEntity);
        Page<BrandEntity> brandEntityPage = new PageImpl<>(brandEntityList);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntityPage);
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(new Brand(1L, "Electronics", "All electronic items"));

        PagedResult<Brand> pagedResult = brandJpaAdapter.getAllBrands(pagination, sortCriteria);

        assertEquals(1, pagedResult.getTotalPages(), "Expected total pages to be 1.");
        assertEquals(1, pagedResult.getTotalElements(), "Expected total elements to be 1.");
    }

    @Test
    void getAllBrandsWhenNotExist() {
        Pagination pagination = new Pagination(0, 10);
        SortCriteria sortCriteria = new SortCriteria("name", SortDirection.ASC);

        List<BrandEntity> brandEntityList = Collections.emptyList();
        Page<BrandEntity> brandEntityPage = new PageImpl<>(brandEntityList);

        when(brandRepository.findAll(any(Pageable.class))).thenReturn(brandEntityPage);

        PagedResult<Brand> pagedResult = brandJpaAdapter.getAllBrands(pagination, sortCriteria);

        assertEquals(0, pagedResult.getTotalPages(), "Expected total pages to be 0.");
        assertEquals(0, pagedResult.getTotalElements(), "Expected total elements to be 0.");
    }


}
