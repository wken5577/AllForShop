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

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.USER;
    }

    public User(String username, String password, String email, SocialProvider socialProvider) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.socialProvider = socialProvider;
        this.role = Role.USER;
    }



    public String getRoleKey() {
        return this.role.getKey();
    }
}
