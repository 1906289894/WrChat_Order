package com.wb.order.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "买家姓名必填")
    private String name;

    @NotEmpty(message = "买家手机号必填")
    private String phone;

    @NotEmpty(message = "买家openid必填")
    private String openid;

    @NotEmpty(message = "买家地址必填")
    private String address;

    @NotEmpty(message = "购物车信息不能为空")
    private String items;
}
