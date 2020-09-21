package com.atguigu.gulimall.cart.interceptor;

import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.constant.CartConstant;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.cart.to.UserInfoTo;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class CartInterceptor implements HandlerInterceptor {
    public static  ThreadLocal<UserInfoTo> threadLocal=new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //本服务的request获取request能获取到登录的session
        //但是远程调用的request？
        HttpSession session = request.getSession();
        UserInfoTo userInfoTo = new UserInfoTo();
        //如果登陆了用户信息对象userInfoTo就设置UserId的值
        //OrderServiceImpl远程调用的时候member为空
        MemberRespVo member = (MemberRespVo)session.getAttribute(AuthServerConstant.LOGIN_USER);
        if(member!=null){
            userInfoTo.setUserId(member.getId());
        }
        //如果没登录，就获取cookie，且cookie的key名字是uer-key，用户信息设置user-key的值且表示为临时用户
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie:cookies){
                String name=cookie.getName();
                if(name.equals(CartConstant.TEMP_USER_COOKIE_NAME)){
                    userInfoTo.setUserKey(cookie.getValue());
                    userInfoTo.setTempUser(true);
                }
            }
        }
        //如果没登录且cookie中都没有user-key，那么随机生成user-key，userinfo设置userkey
        if(StringUtils.isEmpty(userInfoTo.getUserKey())){
            String uuid= UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }
        //用户信息保存在threadlocal对象
        threadLocal.set(userInfoTo);
        return  true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo=threadLocal.get();
        if(!userInfoTo.isTempUser()){
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            cookie.setDomain("gulimall.com");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }

    }
}
