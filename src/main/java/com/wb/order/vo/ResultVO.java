package com.wb.order.vo;
/*
* http请求返回的最外层对象
* */

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    //提示码
    private Integer code;
    //提示信息
    private String msg;
    //具体内容
    private T data;
}
