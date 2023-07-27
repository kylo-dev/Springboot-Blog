package com.blogproject.myblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Security Filter를 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근시 권한 및 인증을 미리 체크
public class SecurityConfig {

    @Bean // password를 해쉬 함수를 적용해줌 - 암호화
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // csrf 토큰 비활성화
                .authorizeHttpRequests() // 어떤 request 요청이 온다면 ->
                    .antMatchers("/","/auth/**","/js/**", "/css/**", "/image/**") // 해당 경로들은 인증없이 이용 가능
                    .permitAll() // 접근 허용
                    .anyRequest() // 다른 요청들의 경우
                    .authenticated() // 인증이 필요함 - 없는 경우 접근 제한 페이지를 보여줌
                .and() // and
                .formLogin() // 로그인 폼 설정
                    .loginPage("/auth/loginForm") // 로그인 경로 설정
                    .defaultSuccessUrl("/"); // 로그인 성공시 리다이렉션 경로 설정

        return httpSecurity.build();
    }

    /**
     * httpSecurity
     *                 .csrf().disable()
     *                 .formLogin().disable();
     *
     *         return httpSecurity
     *                 .authorizeHttpRequests(
     *                         authorize -> authorize
     *                                 .antMatchers("/**").permitAll()
     *                                 // .antMatchers("/login").permitAll()
     *                                 .anyRequest().authenticated()
     *                 )
     *                 //.httpBasic(Customizer.withDefaults()) Jwt 사용할 rest api 방식이면 해줄필요없음
     *                 .build();
     */

}
