package com.emazon.stock.api.infraestructure;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.infraestructure.output.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock.api.infraestructure.output.jpa.entity.BrandEntity;
import com.emazon.stock.api.infraestructure.output.jpa.mapper.BrandEntityMapper;
import com.emazon.stock.api.infraestructure.output.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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


}
