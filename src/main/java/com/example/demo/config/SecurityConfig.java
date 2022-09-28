package com.example.demo.config;

import com.example.demo.config.auth.CpAuthenticationFailureHandler;
import com.example.demo.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final MemberService memberService;

    private final CpAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login", "/join","/joinAdd").permitAll() // 누구나 접근 허용
                .antMatchers("/userInfo","/detailOne").hasAuthority("ROLE_USER") // USER, ADMIN만 접근 가능
                .anyRequest().authenticated() //나머지는 권한의 종류 상관없이 권한있으면 접근가능
                .and()
                .formLogin(
                        formLogin -> formLogin
                                //로그인 페이지 url지정
                                .loginPage("/login")
                                //로그인 form action 지정
                                .loginProcessingUrl("/userLogin")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                //성공하면 어디로 보낼지
                                .defaultSuccessUrl("/board",true)
                                //에러 handler
                                .failureHandler(customAuthenticationFailureHandler)
                                .permitAll()
                )
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied")
                .and()
                //세션 관리에 대한 설정을 위한 메서드
                .sessionManagement(
                        s->s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)//항상 세션을 생성
                                .sessionFixation(sf ->sf.changeSessionId())// 인증할때마다 새로운 세션아이디를 발급 다른 모든 세션속성 유지
                                .maximumSessions(1) // 한 유저가 가질수 있는 세션의 갯수를 1개로 제한 동시접속 방지
                                .maxSessionsPreventsLogin(true)
                                .expiredUrl("/login")
                );
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(memberService);
    }
}