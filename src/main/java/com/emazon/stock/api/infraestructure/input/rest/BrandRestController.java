package com.emazon.stock.api.infraestructure.input.rest;
import com.emazon.stock.api.application.dto.BrandRequest;
import com.emazon.stock.api.application.dto.BrandResponse;
import com.emazon.stock.api.application.handler.IBrandHandler;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestController {


    private final IBrandHandler brandHandler;

    @GetMapping("/all")
    public ResponseEntity<PagedResult<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        PagedResult<BrandResponse> pagedBrands = brandHandler.getAllBrands(page, size, sortBy, direction.toUpperCase());
        return ResponseEntity.ok(pagedBrands);
    }


    @PostMapping("/save")
    public ResponseEntity<Void> saveBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.saveBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
