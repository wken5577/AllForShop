package com.shop.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.user.entity.SocialProvider;
import com.shop.user.entity.User;
import com.shop.user.repository.UserRepository;

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

    public void save(String username, String password, String email) {
        Optional<User> userEntity = userRepository.findByUsername(username);
        if(userEntity.isPresent()) throw new IllegalStateException("이미 존재하는 회원 이름입니다 : " + username);

        String encPwd = passwordEncoder.encode(password);
        User user = User.createNormalUser(username, encPwd, email);
        userRepository.save(user);
    }

    public void register(String username, Long id) {
        Optional<User> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) throw new IllegalStateException("존재하지 않는 회원입니다");

        User user = userEntity.get();
        user.register(username);
    }
}
