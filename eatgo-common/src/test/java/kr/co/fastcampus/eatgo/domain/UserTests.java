package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserTests {

    @Test
    public void creation() {
        User user = User.builder()
                .email("duswp220@gmail.com")
                .name("Tester")
                .level(1L)
                .build();

        assertThat(user.getName(), is("Tester"));
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(true));


        user.deactivate();
        assertThat(user.isActive(), is(false));

    }

    @Test
    public  void restaurantOwner() {
        User user = User.builder().level(1L).build();

        assertThat(user.isRestaurantOwner(), is(false));

        user.setRestaurantId(1004L);

        assertThat(user.isRestaurantOwner(), is(true));
        assertThat(user.getRestaurantId(), is(1004L));

    }
}