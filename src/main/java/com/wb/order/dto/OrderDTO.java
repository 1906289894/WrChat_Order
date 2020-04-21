package com.wb.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wb.order.domain.OrderDetail;
import com.wb.order.enums.OrderStatusEnum;
import com.wb.order.enums.PayStatusEnum;
import com.wb.order.utils.EnumUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//订单传输对象
@Data
public class OrderDTO {

    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    //订单状态，默认为0新下单
    private Integer orderStatus;
    //支付状态，默认为0未支付
    private Integer payStatus;

    private Date createTime;

    private Date updateTime;

    //以上字段同OrderMsdter，下面的list为一对多关系
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtils.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtils.getByCode(payStatus,PayStatusEnum.class);
    }

}
