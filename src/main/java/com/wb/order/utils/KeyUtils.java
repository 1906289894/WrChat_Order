package com.wb.order.utils;

import java.util.Random;


public class KeyUtils {

    /**
     * 生成唯一的主键
     * 格式：毫秒时间字符串+六位随机数
     * @return
     */
    public static synchronized String genUnqueKey(){
        Random random = new Random();

        //生成六位随机数
        Integer a = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+ String.valueOf(a);
    }

}
