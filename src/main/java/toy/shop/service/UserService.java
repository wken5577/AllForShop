package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.User;
import toy.shop.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public Long save(String username, String password, String email) {
        Optional<User> userEntity = userRepository.findByUsername(username);
        if(!userEntity.isEmpty()) throw new IllegalStateException("이미 존재하는 회원 이름입니다 : " + username);

        String encPwd = passwordEncoder.encode(password);
        User user = new User(username, encPwd, email);
        userRepository.save(user);

        return user.getId();
    }
}
