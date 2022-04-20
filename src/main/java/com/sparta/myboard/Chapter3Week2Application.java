package com.sparta.myboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.DispatcherServlet;

@EnableJpaAuditing
@SpringBootApplication
public class Chapter3Week2Application {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(Chapter3Week2Application.class, args);

        //https://velog.io/@gillog/Spring-404-Error-Custom%ED%95%98%EA%B8%B0-ExceptionHandler%EC%82%AC%EC%9A%A9-NoHandlerFoundException-Throw-%EC%95%88%EB%90%A0%EB%95%8C
        DispatcherServlet dispatcherServlet = (DispatcherServlet) ac.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
