package com.yy.blog.article.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 * @author zhengjm5
 * @date 2018/2/11 14:02
 */
public class ArticleWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{

    @Autowired
    private ArticleInterceptor articleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(articleInterceptor).addPathPatterns("/article/**");
        super.addInterceptors(registry);
    }
}
