package toy.shop.web.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.shop.service.UserService;
import toy.shop.web.dto.UserDto;
import toy.shop.web.dto.error.UserErrorDto;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new UserErrorDto(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
        }

        try{
            Long id = userService.save(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
            return new ResponseEntity(id, HttpStatus.OK);

        }catch (IllegalStateException e){
            return new ResponseEntity(new UserErrorDto("username", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
