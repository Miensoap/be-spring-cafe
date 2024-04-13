package codesquad.springcafe;

import codesquad.springcafe.user.interceptor.LoginCheckInterceptor;
import codesquad.springcafe.user.interceptor.NavbarSetInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/user/*")
                .excludePathPatterns("/user/log*") // 로그인, 로그아웃 제외
                .excludePathPatterns("/user/form*"); // 회원가입 제외

        registry.addInterceptor(new NavbarSetInterceptor())
                .addPathPatterns("/*")
                .addPathPatterns("/*/*")
                .excludePathPatterns("/*/*/*");
    }
}