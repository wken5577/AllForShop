package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "user_sequence",sequenceName ="user_sequence")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id @GeneratedValue(generator = "user_sequence")
    private Long id;

    private String username;

    private String profile;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String profile, String email) {
        this.username = username;
        this.profile = profile;
        this.email = email;
        this.role = Role.USER;
    }

    public void update(String username, String profile){
        this.profile = profile;
        this.username = username;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
