package com.emazon.stock.api.infraestructure.input.rest;
import com.emazon.stock.api.application.dto.CategoryRequest;
import com.emazon.stock.api.application.dto.CategoryResponse;
import com.emazon.stock.api.application.handler.ICategoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController  {

    private final ICategoryHandler categoryHandler;

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryResponse>> getCategories(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(categoryHandler.getAllCategories(pageable));
    }


    @PostMapping("/save")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryHandler.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
