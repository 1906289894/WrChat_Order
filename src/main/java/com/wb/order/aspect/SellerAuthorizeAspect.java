package com.wb.order.aspect;

import com.wb.order.constant.CookieConstant;
import com.wb.order.exception.SellerAuthorizeException;
import com.wb.order.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    //拦截com.aiolos.wxdc.controller包下以Aop开头的Controller中的所有public方法
    //并且不拦截com.aiolos.wxdc.controller包下AopLoginController中的所有public方法
    @Pointcut("execution(public * com.wb.order.controller.Aop*.*(..))"+
            "&& !execution(public * com.wb.order.controller.AopLoginController.*(..))")
    public void verify(){}

    /**
     * 在切入点之前，执行该方法进行身份验证
     */

    @Before("verify()")
    public void doVerify(){
//        获取httpservletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取cookie,并与redis中的进行对比
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            //没有登陆
            log.info("[登陆校验] cookie中查询不到token");
            throw new SellerAuthorizeException();
        }
        //到redis中查询cookie中的token是否存在
        String tokenValue = null; //从redis中查询
        if (StringUtils.isEmpty(tokenValue)){
            log.info("[登录校验] redis中查询不到token");
            throw new SellerAuthorizeException();
        }

    }
}
