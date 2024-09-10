package com.emazon.stock.api.infraestructure.input.rest;
import com.emazon.stock.api.application.dto.ProductRequest;
import com.emazon.stock.api.application.dto.ProductResponse;
import com.emazon.stock.api.application.handler.IProductHandler;
import com.emazon.stock.api.domain.utils.pagination.PagedResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final IProductHandler productHandler;

    @PostMapping("/save")
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        productHandler.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<PagedResult<ProductResponse>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        PagedResult<ProductResponse> products = productHandler.getAllBrands(page, size, sortBy, direction);
        return ResponseEntity.ok(products);
    }


}



