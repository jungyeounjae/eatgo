package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RestaurantTests {

    @Test
    public void creation() {

        // Restaurant restaurant = new Restaurant(1004L,"Bob zip", "Seoul");

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build(); // build 메소드를 호출하여 immutable 객체를 생성

        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getName(), is("Bob " + "Zip"));
        assertThat(restaurant.getAddress(), is("Seoul"));
    }

    @Test
    public void information() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob Zip")
                .address("Seoul")
                .build();

        assertThat(restaurant.getInformation(), is("Bob Zip in Seoul"));
    }

}