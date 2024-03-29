package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired // 주입
    private MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getMenuItems(Long restaurantId) {
        List<MenuItem> menuItems =menuItemRepository.findAllByRestaurantId(restaurantId);

        return menuItems;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for (MenuItem menuItem: menuItems) {
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if (menuItem.isDestroy()) {
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        menuItem.setRestaurantId(restaurantId);
        menuItemRepository.save(menuItem);
    }
}
