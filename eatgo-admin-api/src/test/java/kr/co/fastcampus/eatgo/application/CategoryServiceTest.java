package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Category;
import kr.co.fastcampus.eatgo.domain.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/*
　Spring　テストではなくて
　一般のJ-Unitテスト
 */
public class CategoryServiceTest {

    private CategoryService CategoryService;

    @Mock
    private CategoryRepository CategoryRepository;

    @Before
    public void setUp() {
        //　iniMocksにより、@MockオブジェクトがMockオブジェクトに変換される。
        MockitoAnnotations.initMocks(this);

        CategoryService = new CategoryService(CategoryRepository);
    }
    @Test
    public void getCategories() {
        List<Category> mockCategories = new ArrayList<>();
        mockCategories.add(Category.builder().name("Korean Food").build());

        given(CategoryRepository.findAll()).willReturn(mockCategories);

        List<Category> Categories = CategoryService.getCategories();

        Category Category = Categories.get(0);
        assertThat(Category.getName(), is("Korean Food"));
    }

    @Test
    public void addCategory() {
        Category Category = CategoryService.addCategory("Korean Food");

        verify(CategoryRepository).save(any());

        assertThat(Category.getName(),is( "Korean Food"));
    }
}