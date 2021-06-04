package com.spring.mvc.web.config;

import com.spring.mvc.web.interceptor.AuthInterceptor;
import com.spring.mvc.web.interceptor.AutoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 인터셉터의 등록 및 적용 URL 설정
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //사용할 인터셉터를 주입받는다.
    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private AutoLoginInterceptor autoLoginInterceptor;

    //인터셉터 적용 설정 메서드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //비회원이 볼 수 없는 페이지 설정
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/board/*") //해당 인터셉터를 적용할 구간
                //인터셉터를 적용하지 않을 예외 구간
                .excludePathPatterns("/board/list", "/board/content");

        //자동로그인 인터셉터 설정
        registry.addInterceptor(autoLoginInterceptor)
                .addPathPatterns("/**");

    }
}
