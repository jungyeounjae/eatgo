package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor //
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Long restaurantId;

    private String name;

    @Transient  // DB에 저장하지 않는다.
    @JsonInclude(JsonInclude.Include.NON_DEFAULT) // boolen의 default는 false이고, default값이 아닌 경우에는 포함시키지 않는
    private boolean destroy;
}
