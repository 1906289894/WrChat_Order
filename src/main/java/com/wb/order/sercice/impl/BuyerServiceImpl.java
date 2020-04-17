package com.wb.order.sercice.impl;

import com.wb.order.dto.OrderDTO;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.sercice.BuyerService;
import com.wb.order.sercice.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;
    /**
     * 判断订单id与openid是否属于一个用户
     * @param openid
     * @param orderid
     * @return
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {
        return this.checkOrderOwner(openid,orderid);
    }

    /**
     * 判断是否是自己的订单
     * @param openid
     * @param orderid
     * @return
     */
    @Override
    public OrderDTO cancelOneOrder(String openid, String orderid) {
        OrderDTO orderDTO = this.checkOrderOwner(openid, orderid);
        if (orderDTO == null){
            log.error("[取消订单] 查不到该订单");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    //判断是否是自己的订单
    private OrderDTO checkOrderOwner(String openid,String orderid){
        OrderDTO orderDTO = orderService.findById(orderid);
        if (orderDTO == null){
            return null;
        }

        //判断是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error(" [查询订单] 订单的openid不一致，openid={}",openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
