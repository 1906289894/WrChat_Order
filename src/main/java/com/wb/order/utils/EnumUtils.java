package com.wb.order.utils;

import com.wb.order.enums.CodeEnum;

public class EnumUtils {

    //要求泛型中的类必须有getCode()方法
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()){
            if (code.equals(each.getCode()))
            return each;
        }
        return null;
    }
}
