package com.wb.order.controller;

import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    //这里的token要和微信测试号网页填写的token一样
    public static final String TOKEN = "43t546dfghru5645yrtey45wty";

    @Autowired
    private WxMpService wxMpService;


    @GetMapping("/checkToken")
    public void checkTOken(String signature, String timestamp, String nonce
                            , String echostr, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        //将token、timestamp、nonce三个参数字典排序
        log.info("signature={}",signature);
        log.info("timestamp={}",timestamp);
        log.info("nonce={}",nonce);
        log.info("echostr={}",echostr);
        log.info("TOKEN={}",TOKEN);
        String[] params = new String[] { TOKEN, timestamp, nonce };
        Arrays.sort(params);
        //将三个参数字符串拼接成一个字符串进行sha1加密
        String clearText = params[0] + params[1] + params[2];
        String algorithm = "SHA-1";
        String sign = new String(
                org.apache.commons.codec.binary.Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
        // 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(sign)) {
            response.getWriter().print(echostr);
        }
    }

    /**
     * 第三方SDK获取openid的方式
     * 用户同意授权，获取code的REDIRECT_URI
     * 如果用户同意授权，页面将跳转至该接口
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1. 统一配置，见WechatMPConfig
        //2. 调用
        //重定向的url
        String url = "http://wbsell.nat300.top/sell/wechat/userInfo";
        //重定向的微信授权页面
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("[微信网页授权] 获取code，result={}",redirectUrl);
        //重定向获取access_token
        return "redirect:"+redirectUrl;
    }

    /**
     * 上述/authorize接口重定向的方法
     * 用于获取access_token和openid
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl){
        log.info("进入 auth()方法 ...");
        log.info("code={}",code);

        //获取access_token
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
             wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] 错误, {}",e);
            throw new SellException(ResultEnum.WX_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }

        //获取openid
        String openId = wxMpOAuth2AccessToken.getOpenId();
        System.out.println(openId);
        //重定向
        return "redirect:"+returnUrl+"?openid="+openId;
    }

}
