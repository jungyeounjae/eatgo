package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public boolean isAdmin() {
        return level >= 3;
    }

    public boolean isActive() {
        return level > 0L;
    }

    public void deactivate() {
        level = 0L;
    }

    /*
        jsonignore : 빈 클래스를 json객체로 만들 때 스킵하고 싶은 필드에 선
     */
    @JsonIgnore
    public String getAccessToken() {
        return password.substring(0, 10);
    }
}
