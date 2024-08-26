package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.BrandRequest;
import com.emazon.stock.api.application.dto.BrandResponse;
import com.emazon.stock.api.application.mapper.BrandRequestMapper;
import com.emazon.stock.api.application.mapper.BrandResponseMapper;
import com.emazon.stock.api.domain.api.IBrandServicePort;
import com.emazon.stock.api.domain.model.Brand;
import com.emazon.stock.api.domain.utils.PagedResult;
import com.emazon.stock.api.domain.utils.Pagination;
import com.emazon.stock.api.domain.utils.SortCriteria;
import com.emazon.stock.api.domain.utils.SortDirection;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BrandHandler implements IBrandHandler{

    private final IBrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;

    private final BrandResponseMapper brandResponseMapper;


    @Override
    public void saveBrand(BrandRequest brandRequest) {
        Brand brand = brandRequestMapper.toBrand(brandRequest);
        brandServicePort.saveBrand(brand);
    }

    @Override
    public PagedResult<BrandResponse> getAllBrands(int page, int size, String sortBy, String direction) {
        Pagination pagination = new Pagination(page, size);
        SortDirection sortDirection = SortDirection.valueOf(direction.toUpperCase());
        SortCriteria sortCriteria = new SortCriteria(sortBy, sortDirection);
        PagedResult<Brand> brandPagedResult = brandServicePort.getAllBrands(pagination, sortCriteria);
        List<BrandResponse>brandResponses = brandPagedResult.getContent()
                .stream()
                .map(brandResponseMapper::toResponse).toList();
        return new PagedResult<>(brandResponses, brandPagedResult.getTotalElements(), brandPagedResult.getTotalPages());
    }
}
