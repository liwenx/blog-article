package com.yy.blog.article.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 文章拦截器(设置浏览量)
 * @author zhengjm5
 * @date 2018/2/10 18:05
 */
@Component
public class ArticleInterceptor implements HandlerInterceptor {

    /**
     * redis操作类
     */
    @Resource
    private RedisTemplate<String, AtomicInteger> redisTemplate;

    /**
     * 读写锁
     */
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * 在请求处理之前进行调用（Controller方法调用之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取文章id
        String articleId = httpServletRequest.getParameter("articleId");
        if (StringUtils.isEmpty(articleId)) {
            return false;
        }
        //上读锁，其他线程只能读不能写
        AtomicInteger value = null;
        reentrantReadWriteLock.readLock().lock();
        try {
            value = redisTemplate.opsForValue().get(articleId);
            if (value == null) {
                //释放读锁
                reentrantReadWriteLock.readLock().unlock();
                //写锁
                reentrantReadWriteLock.writeLock().lock();
                try {
                    if (value == null) {
                        //设置浏览量
                        value = new AtomicInteger(1);
                        redisTemplate.opsForValue().set(articleId, value);
                    } else {
                        value = redisTemplate.opsForValue().get(articleId);
                        value.set(value.addAndGet(1));
                    }
                } finally {
                    //释放写锁
                    reentrantReadWriteLock.writeLock().unlock();
                }
            } else {
                //释放读锁
                reentrantReadWriteLock.readLock().unlock();
                //写锁
                reentrantReadWriteLock.writeLock().lock();
                //更改浏览量
                value = redisTemplate.opsForValue().get(articleId);
                value.set(value.addAndGet(1));
                redisTemplate.opsForValue().set(articleId, value);
                //释放写锁
                reentrantReadWriteLock.writeLock().unlock();
            }
            //然后再上读锁
            reentrantReadWriteLock.readLock().lock();
        } finally {
            //释放读锁
            reentrantReadWriteLock.readLock().unlock();
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
