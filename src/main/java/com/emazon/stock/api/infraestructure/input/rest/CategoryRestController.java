package com.emazon.stock.api.infraestructure.input.rest;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.handler.ICategoryHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController  {

    private final ICategoryHandler categoryHandler;
    @PostMapping("/save")
    public ResponseEntity<Void> saveCategory(@Valid  @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
