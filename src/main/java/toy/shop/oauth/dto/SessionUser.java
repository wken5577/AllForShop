package toy.shop.oauth.dto;

import lombok.Getter;
import toy.shop.entity.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String profile;


    public SessionUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getUsername();
        this.profile = user.getProfile();
    }

}
