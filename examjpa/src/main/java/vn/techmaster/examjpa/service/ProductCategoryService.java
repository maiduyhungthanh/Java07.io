package vn.techmaster.examjpa.service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.examjpa.model.Category;
import vn.techmaster.examjpa.model.Product;
import vn.techmaster.examjpa.repository.CategoryRepo;
import vn.techmaster.examjpa.repository.ProductRepo;


@Service
public class ProductCategoryService {
  @PersistenceContext
  private EntityManager em;
  @Autowired private CategoryRepo categoryRepo;
  @Autowired private ProductRepo productRepo;


  @Transactional
  public void generateCategoryProduct() {

    Category catElectronics = new Category("Electronics");
    Category catFoodBeverages = new Category("Food Beverages");
    Category catFashion = new Category("Fashion");

    Product laptopDell = new Product("Dell Laptop", catElectronics);
    Product mobileVinSmart = new Product("Vinsmart", catElectronics);

    Product beerHanoi = new Product("Beer Hanoi", catFoodBeverages);
    Product kimChi = new Product("Kim Chi", catFoodBeverages);

    Product bitisHunter = new Product("Bitis Hunter", catFashion);
    Product shirt511 = new Product("511 Shirt", catFashion);
    Product jeanVietthang = new Product("Jean Viet Thang", catFashion);

    em.persist(catElectronics);
    em.persist(catFoodBeverages);
    em.persist(catFashion);

    em.persist(laptopDell);
    em.persist(mobileVinSmart);

    em.persist(beerHanoi);
    em.persist(kimChi);
    
    em.persist(bitisHunter);
    em.persist(shirt511);
    em.persist(jeanVietthang);

    //xóa Product KimChi
    // em.remove(kimChi); 
  }
  public List<Category> getAllCategory() {
    return categoryRepo.findAll();
}
public List<Product> getAllProduct(){
  return productRepo.findAll();
}
}
