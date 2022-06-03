package vn.techmaster.examjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.examjpa.service.ProductCategoryService;

@Component
public class AppRunner implements CommandLineRunner {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Override
  public void run(String... args) throws Exception {
    productCategoryService.generateCategoryProduct();
  }

}
