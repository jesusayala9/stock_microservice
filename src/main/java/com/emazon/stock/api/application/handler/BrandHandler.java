package com.emazon.stock.api.application.handler;
import com.emazon.stock.api.application.dto.BrandRequest;
import com.emazon.stock.api.application.mapper.BrandRequestMapper;
import com.emazon.stock.api.application.mapper.BrandResponseMapper;

import com.emazon.stock.api.domain.api.IBrandServicePort;

import com.emazon.stock.api.domain.model.Brand;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
