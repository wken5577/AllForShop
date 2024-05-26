package com.shop.user.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.shop.user.entity.Role;
import com.shop.user.entity.User;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserJoinReqDto {

    @NotBlank(message = "username이 공백일 수 없습니다.")
    private String username;
    @NotBlank(message = "password가 공백일 수 없습니다.")
    private String password;
    @NotBlank(message = "email이 공백일 수 없습니다.")
    private String email;
    private Role role;

    public UserJoinReqDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

}
