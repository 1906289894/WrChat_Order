package com.wb.order.sercice;

import com.wb.order.dto.OrderDTO;

/*
* 买家
* */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderid);

    //取消一个订单
    OrderDTO cancelOneOrder(String openid,String orderid);
}
