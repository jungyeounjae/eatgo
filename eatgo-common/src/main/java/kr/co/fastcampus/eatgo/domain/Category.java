package kr.co.fastcampus.eatgo.domain;

import lombok.AllArgsConstructor;  // It automatically creates a constructor with an entire factor.
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id // primary key role
    @GeneratedValue
    private long id;

    private String name;

    //test
}
