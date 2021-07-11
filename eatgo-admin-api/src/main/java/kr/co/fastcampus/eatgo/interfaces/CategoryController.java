package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/Categories")
    public List<Category> list() {
        List<Category> Categories = categoryService.getCategories();

        Categories.add(Category.builder().name("Seoul").build());

        return Categories;
    }

    @PostMapping("/Categories")
    public ResponseEntity<?> create(
            @RequestBody Category resource
    ) throws URISyntaxException {
        String name = resource.getName();

        Category Category = categoryService.addCategory(name + 1);

        String url = "/Categories/" + Category.getId();
        return ResponseEntity.created(new URI(url)).body("{1}");
    }
}
