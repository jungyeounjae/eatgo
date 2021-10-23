package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @NotEmpty
    private String email;

    @NotEmpty
    @Setter
    private String name;

    @NotNull
    @Setter
    private Long level;

    private String password;

    @Setter
    private Long restaurantId;

    public boolean isAdmin() {
        return level >= 3;
    }

    public boolean isActive() {
        return level > 0L;
    }

    public void deactivate() {
        level = 0L;
    }

    public void setRestaurantId(Long restaurantId) {
        this.level = 50L;
        this.restaurantId = restaurantId;
    }

    /*
        레스토랑 주인인지 아닌지 확인
     */
    public boolean isRestaurantOwner() {
        return level == 50L;
    }

    /*
        jsonignore : 빈 클래스를 json객체로 만들 때 skip하고 싶은 필드에 선언
     */
//    @JsonIgnore
//    public String getAccessToken() {
//        return password.substring(0, 10);
//    }
}
