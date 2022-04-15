package toy.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import toy.shop.security.oauth.PrincipalOAuth2Service;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOAuth2Service oAuth2Service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**","/img/**","/item/**", "/loginForm", "/join","/category").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                            .loginProcessingUrl("/login")
                                .loginPage("/loginForm")
                                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .defaultSuccessUrl("/")
                            .userInfoEndpoint()
                                .userService(oAuth2Service);

    }
}
