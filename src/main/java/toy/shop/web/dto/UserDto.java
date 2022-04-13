package toy.shop.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import toy.shop.entity.Role;
import toy.shop.entity.User;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "username이 공백일 수 없습니다.")
    private String username;
    @NotBlank(message = "password가 공백일 수 없습니다.")
    private String password;
    @NotBlank(message = "email이 공백일 수 없습니다.")
    private String email;
    private Role role;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

}
