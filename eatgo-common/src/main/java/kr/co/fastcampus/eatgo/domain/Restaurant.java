package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.core.convert.support.GenericConversionService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue
    @Setter
    private Long id;

    // @Setter // 특정 속성에 롬북을 지정한 경우, 해당 속성은 해당 어노테이션 롬북 밖에 생성하지 않는다.
    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Transient // 임시 처리를 하기 위한 annotation, 해당 멤버는 DB 처리를 하지 않겠다는 의미
    @JsonInclude(JsonInclude.Include.NON_NULL) // Null의 경우에는 처리 하지 않겠다는 의
    private List<MenuItem> menuItems;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

    public Object getInformation() {
        return name + " in "  + address;
    }

    public void setMenuItem(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
