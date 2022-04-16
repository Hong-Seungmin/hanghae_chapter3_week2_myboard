package com.sparta.myboard.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
// h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
// 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
        http.csrf().ignoringAntMatchers("/api/**");

        http.authorizeRequests()
// image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
// css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
// 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/api/**").permitAll()
// 그 외 어떤 요청이든 '인증'
                .anyRequest().authenticated().and()
// 로그인 기능
                .formLogin()
//                .loginPage("/user/login")
                .defaultSuccessUrl("/asd").failureUrl("/user/login?error").permitAll().and()
// 로그아웃 기능
                .logout().permitAll();



    }

//    //----------------------------------
//    // FailureHandler
//    //----------------------------------
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//    }
//
//
//    //----------------------------------
//    // SuccessHandler
//    //----------------------------------
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        RequestUserLoginDto loginDto = new RequestUserLoginDto();
//        Principal userPrincipal = request.getUserPrincipal();
//        userPrincipal.
//    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//        MyResult result = new MyResult("認証成功"); // JSONにするオブジェクト
//        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
//        httpMessageConverter.write(result, CONTENT_TYPE_JSON, outputMessage); // Responseに書き込む
//        response.setStatus(HttpStatus.OK.value()); // 200 OK.
//    }

}