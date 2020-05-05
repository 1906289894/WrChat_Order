package com.wb.order.enums;

import lombok.Getter;
import org.aspectj.apache.bcel.classfile.Code;

@Getter
public enum ResultEnum {
    PARAM_ERROR(1,"参数不正确"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    CART_EMPTY_ERROR(18,"购物车为空错误"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    WX_MP_ERROR(20,"微信授权错误"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21,"微信异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(22,"订单取消成功"),
    ORDER_FINISH_SUCCESS(23,"订单完结成功"),
    PRODUCT_STATUS_ERROR(24,"商品上下架状态错误"),
    PRODUCT_STATUS_SUCCESS(25,"商品上下架状态修改成功"),
    PRODUCT_SAVE_SUCCESS(26,"商品保存成功"),
    CATEGORY_NOT_EXIST(27,"类目不存在"),
    CATEGORY_SAVE_SUCCESS(28,"类目保存成功"),
    LOGIN_FAILED(29,"登陆失败"),
    LOGIN_SUCCESS(30,"登陆成功"),
    LOGOUT_SUCCESS(31,"退出成功"),
    LOGOUT_FAILED(32,"退出失败"),
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
