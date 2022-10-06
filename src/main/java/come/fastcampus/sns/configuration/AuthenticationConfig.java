package come.fastcampus.sns.configuration;

import come.fastcampus.sns.configuration.filter.JwtTokenFilter;
import come.fastcampus.sns.exception.CustomAuthenticationEntryPoint;
import come.fastcampus.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserService userService;

    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/*/users/join", "/api/*/users/login").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        return http.build();
    }

    /**
     * sessionCreationPolicy(SessionCreationPolicy.STATELESS) :
     * HTTP Request가 들어왔을 때, 어떻게 Security 설정할것인 지 정의를 해둔 것이다.
     * 필터를 설정해서 들어온 토큰이 어떤 유저를 가리키는지 체크하는 로직
     */


    /**
     * regexMatchers : 문자열들을 규칙을 만들어서 해당 규칙들만 통과를 시키거나 설정을 한다.
     * ^(?!/api/).* : /api/ 로 시작하는 path들만 통과를 시킨다.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().regexMatchers("^(?!/api/).*");
    }
}
