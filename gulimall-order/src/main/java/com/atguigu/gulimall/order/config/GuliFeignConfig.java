package com.atguigu.gulimall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class GuliFeignConfig {
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes requestAttributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    //异步调用远程服务，request为空
                    //异步调用远程方法用的都是这个拦截器，拦截器需要从RequestContextHolder获取RequestAttributes
                    //然后再根据RequestAttributes获取到请求，把请求的请求头赋值到requestTemplate上
                    //所以为空原因就在于RequestContextHolder没有包含原生请求的RequestAttributes
                    HttpServletRequest request = requestAttributes.getRequest();
                    if (request != null) {
                        //同步请求头，主要是同步Cookie
                        requestTemplate.header("Cookie", request.getHeader("Cookie"));
                    }
                }
            }
        };
    }
}
