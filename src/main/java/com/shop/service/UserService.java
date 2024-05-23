package com.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.entity.SocialProvider;
import com.shop.entity.User;
import com.shop.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User findOrCreateUser(String providerId, String email, SocialProvider provider) {
        Optional<User> optionalUser = userRepository.findByProviderId(providerId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User newUser = User.createSocialUser(providerId, email, provider);
            return userRepository.save(newUser);
        }
    }

    public Long save(String username, String password, String email) {
        Optional<User> userEntity = userRepository.findByUsername(username);
        if(!userEntity.isEmpty()) throw new IllegalStateException("이미 존재하는 회원 이름입니다 : " + username);

        String encPwd = passwordEncoder.encode(password);
        User user = User.createNormalUser(username, encPwd, email);
        userRepository.save(user);

        return user.getId();
    }
}
