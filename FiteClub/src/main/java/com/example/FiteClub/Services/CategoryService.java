//package com.example.FiteClub.Services;
//
//import com.example.FiteClub.models.Categories;
//import com.example.FiteClub.repos.CategoriesRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Random;
//
//@Service
//public class CategoryService {
//    @Autowired
//    private CategoriesRepo categoriesRepo;
//
//    public ResponseEntity<String> addCategory(String name) {
//        String upperName = name.toUpperCase();
//
//        if (categoriesRepo.existsByName(upperName)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists.");
//        }
//
//        categoriesRepo.save(new Categories(null, upperName));
//        return ResponseEntity.ok("Category added successfully!");
//    }
//
//
//    public List<Categories> getAllCategories() {
//        return categoriesRepo.getAllCategories();
//    }
//    public  Categories getRandomCategory(){
//        List<Categories> categoriesArr = categoriesRepo.getAllCategories();
//        Random random = new Random();
//        return categoriesArr.get(random.nextInt(categoriesArr.size()));
//
//    }
//}
