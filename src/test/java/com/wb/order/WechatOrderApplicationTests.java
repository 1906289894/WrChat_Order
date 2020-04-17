package com.wb.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WechatOrderApplicationTests {

    //private final Logger log = LoggerFactory.getLogger(WxdcApplicationTests.class);

    @Test
    public void testLog(){
        String name = "imooc";
        String password = "123456";
        log.info("name:{},password:{}",name,password);
        log.debug("debug...");
        log.info("info...");
        log.error("error...");
    }

}
