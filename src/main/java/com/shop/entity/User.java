package com.shop.entity;

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

    private String providerId;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private User(String providerId, String username, String password, String email,
        SocialProvider socialProvider, UserStatus userStatus) {
        this.providerId = providerId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.socialProvider = socialProvider;
        this.userStatus = userStatus;
        this.role = Role.USER;
    }

    public static User createSocialUser(String providerId, String email, SocialProvider socialProvider) {
        return new User(providerId, null, null, email, socialProvider, UserStatus.AUTHORIZED);
    }

    public static User createNormalUser(String username, String password, String email) {
        return new User(null, username, password, email, SocialProvider.NONE, UserStatus.REGISTERED);
    }

    public void register(String username) {
        this.username = username;
        this.userStatus = UserStatus.REGISTERED;
    }
}
