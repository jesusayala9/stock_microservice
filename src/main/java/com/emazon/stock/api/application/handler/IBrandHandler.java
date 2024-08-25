package com.emazon.stock.api.application.handler;

import com.emazon.stock.api.application.dto.BrandRequest;


public interface IBrandHandler {

    void saveBrand(BrandRequest brandRequest);
}
