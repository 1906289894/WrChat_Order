package com.wb.order.controller;

import com.wb.order.constant.CookieConstant;
import com.wb.order.constant.RedisConstant;
import com.wb.order.domain.SellerInfo;
import com.wb.order.enums.ResultEnum;
import com.wb.order.sercice.SellerUserService;
import com.wb.order.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户操作
 */
@Slf4j
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerUserService sellerUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录功能
     * @param openid
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //获取url中的openid与数据库中的openid进行校验
        SellerInfo sellerInfo = sellerUserService.getSellerInfoByOpenId(openid);
        if (sellerInfo == null){
            //用户不存在，则跳转到错误页面
            map.put("msg", ResultEnum.LOGIN_FAILED.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        //将token保存到Redis中
        String token = UUID.randomUUID().toString();
        Integer exptre = RedisConstant.EXPTRE;
        //保存token-openid的key-value到redis,并设置过期时间为expire,过期时间格式为TimeUnit.SECONDS
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,exptre, TimeUnit.SECONDS);
        //将token设置到cookie中
        CookieUtils.set(response, CookieConstant.TOKEN,token,exptre);

        //4. 登录成功，跳转到列表页
        //通过redirect地址跳转最好使用绝对地址，而不要使用相对地址
        map.put("msg",ResultEnum.LOGIN_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 登出功能
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,Object> map){
        //从cookie中查询
        Cookie cookie = CookieUtils.get(request,CookieConstant.TOKEN);
        if (cookie != null){
            //清除reids
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //清除cookie
            CookieUtils.set(response,CookieConstant.TOKEN,null,0);

            map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/success",map);
        }
        map.put("msg",ResultEnum.LOGOUT_FAILED.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/error",map);

    }

}
