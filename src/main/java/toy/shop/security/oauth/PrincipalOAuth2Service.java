package toy.shop.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import toy.shop.entity.User;
import toy.shop.repository.UserRepository;
import toy.shop.web.dto.UserDto;
import toy.shop.security.dto.*;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2Service extends DefaultOAuth2UserService {

    private final OAuth2Provider oAuth2Provider;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();

        String provider = userRequest.getClientRegistration().getRegistrationId();

        User user = oAuth2Provider.makeUser(provider, attributes);
        Optional<User> userEntity = userRepository.findByUsername(user.getUsername());
        if (!userEntity.isEmpty()) {
            return new PrincipalDetail(new UserDto(userEntity.get()),attributes);
        }

        userRepository.save(user);
        return new PrincipalDetail(new UserDto(user),attributes);
    }
}
