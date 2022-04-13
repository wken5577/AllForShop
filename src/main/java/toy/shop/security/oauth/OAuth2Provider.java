package toy.shop.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import toy.shop.entity.SocialProvider;
import toy.shop.entity.User;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2Provider {

    @Value("${oauth2.password}")
    private  String password;
    private final BCryptPasswordEncoder encoder;

    public User makeUser(String provider, Map<String, Object> attributes) {
        if(provider.equals("google")){
           return ofGoogle(attributes);
        }else if(provider.equals("naver")){
            return ofNaver(provider, attributes);
        }
        throw new IllegalArgumentException("저희는 google과 naver 로그인만 지원합니다.");
    }

    private User ofNaver(String provider, Map<String, Object> attributes) {
        Map<String, Object> responseAttributes = (Map<String, Object>)attributes.get("response");

        String username = "naver_" + responseAttributes.get("id");
        String email = (String) responseAttributes.get("email");
        String encPwd = encoder.encode(password);

        User user = new User(username, encPwd, email, SocialProvider.NAVER);

        return user;
    }

    private User ofGoogle(Map<String, Object> attributes) {
        String username = "google_" + attributes.get("sub");

        String email = (String) attributes.get("email");
        String encPwd = encoder.encode(password);

        User user = new User(username, encPwd, email, SocialProvider.GOOGLE);

        return user;
    }


}
