package toy.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toy.shop.entity.User;
import toy.shop.repository.UserRepository;
import toy.shop.security.dto.*;
import toy.shop.web.dto.UserDto;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다 : " + username));
        UserDto userDto = new UserDto(user);

        return new PrincipalDetail(userDto);
    }
}
