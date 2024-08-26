package com.emazon.stock.api.application.handler;

import com.emazon.stock.api.application.dto.BrandRequest;
import com.emazon.stock.api.application.dto.BrandResponse;
import com.emazon.stock.api.domain.utils.PagedResult;


public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);

    PagedResult<BrandResponse> getAllBrands(int page, int size, String sortBy, String direction);
}
