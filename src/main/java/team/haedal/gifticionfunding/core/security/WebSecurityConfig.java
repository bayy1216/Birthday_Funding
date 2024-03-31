package team.haedal.gifticionfunding.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/v3/api-docs/**")
                .requestMatchers("/h2-console/**")
                .requestMatchers("/static/**");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * JWT를 사용하므로 csrf, sessionManagement를 사용하지 않도록 설정한다.
         * 이는 JWT가 토큰 기반이기 때문에 서버에서 세션을 관리하지 않아도 되기 때문이다.
         * csrf 또한 사용하지 않는다. (세션을 사용하지 않기 떄문에 csrf 기능을 사용하지 않는다.)
         */
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /**
         * 인증, 인가 설정
         * requestMatchers() 메서드를 통해 특정 요청에 대한 인증, 인가 설정을 한다.
         * permitAll() 메서드를 통해 특정 요청에 대한 인증 없이 접근을 허용한다.
         *
         * anyRequest() 메서드를 통해 나머지 요청에 대한 인증 설정을 한다.
         * authenticated() 메서드를 통해 인증된 사용자만 접근 가능하도록 설정한다.
         * 어노테이션기반을 사용하지 않는 이유는 시큐리티의 기능 중 하나인 인가 예외처리를 사용하기 위함이다.
         */
        http.authorizeHttpRequests((authorize) ->
                authorize
                        .requestMatchers(
                                "/login", "/signup", "/", "/user",
                                "/api/auth/**","/api/user/signup-email",
                                "/swagger-ui/**"
                        ).permitAll()
                        .anyRequest().authenticated()
        );

        /**
         * 시큐리티 필터체인에서의 예외처리
         * authenticated() 메서드를 통해 인증된 사용자만 접근 가능하도록 설정한다.
         * 1. 인증 예외 처리 (인증되지 않은 사용자) 401
         * 2. 인가 예외 처리 (권한이 없는 사용자) 403
         */
        http.exceptionHandling((exception) -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return http.getSharedObject(AuthenticationManager.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
