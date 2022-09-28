package com.example.demo.config.auth;



import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class CpAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;
        String errPage = "/login/error";
        if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "아이디 또는 비밀번호를 확인하세요.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호를 확인하세요.";
        } else {
            if (exception instanceof DisabledException) {
                errorMessage = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
            } else if (exception instanceof CredentialsExpiredException) {
                errorMessage = exception.getMessage();
                errPage = "/login/error";
            } else if (exception instanceof SessionAuthenticationException) {
                errorMessage = "동일계정이 접속중입니다. 기존계정을 로그아웃하세요.";
            } else {
                errorMessage = "로그인 싪패하엿습니다.";
            }
        }
        var userCd =request.getParameter("userCd");
        log.error("error - {} - {}", errorMessage,userCd);
        request.setAttribute("error", errorMessage);
        request.setAttribute("userCd", userCd);
        log.debug("{}",request.getAttribute("userCd"));
        //this.setDefaultFailureUrl(SecurityConfig.AUTH_PAGE);
       // super.onAuthenticationFailure(request,response,exception);
        AtomicReference<String> defaultFailureUrl = new AtomicReference<>(errPage);
        request.getRequestDispatcher(defaultFailureUrl.get()).forward(request, response);
    }
}
