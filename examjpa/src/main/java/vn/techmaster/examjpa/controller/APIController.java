package vn.techmaster.examjpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.examjpa.model.Category;
import vn.techmaster.examjpa.model.Product;
import vn.techmaster.examjpa.service.ProductCategoryService;

@RestController
public class APIController {
    @Autowired private ProductCategoryService productCategoryService;
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategory() {
      return ResponseEntity.ok().body(productCategoryService.getAllCategory());
    }
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProduct() {
      return ResponseEntity.ok().body(productCategoryService.getAllProduct());
    }
}