package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MenuItemServiceTest {

    private MenuItemService menuItemService;

    /*
       서비스는 레포지토리와 의존 관계를 맺고 있다.
       레포지토리에서는 DB와 관계를 맺고 있지만 여기에서의 테스트는 DB상태나 관계까지 고려할 필요가 없다.
       따라서 우리는 목 객체라는 임의의 객체를 생성해 DB연동 까지 생각하지 않아도 된다.
     */
    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this );
        menuItemService = new MenuItemService(menuItemRepository);
    }

    @Test
    public void getMenuItems() {
        List<MenuItem> mockMenuItems = new ArrayList<>();
        mockMenuItems.add(MenuItem.builder().name("Kimchi").build());

        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(mockMenuItems);

        List<MenuItem> menuItems = menuItemService.getMenuItems(1004L);
        MenuItem menuItem = menuItems.get(0);

        assertThat(menuItem.getName(),is("Kimchi"));
    }

    @Test
    public void bulkUpdate() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        menuItems.add(MenuItem.builder().name("kimchi").build());
        menuItems.add(MenuItem.builder().id(12L).build());
        menuItems.add(MenuItem.builder().id(1004L).destroy(true).build());

        menuItemService.bulkUpdate(1L, menuItems);

        // menuItemRepository.save()  가 2번 실행 했는지 체크
        verify(menuItemRepository, times(2)).save(any());
        verify(menuItemRepository, times(1)).deleteById(eq(1004L));
    }
}