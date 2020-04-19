package com.wb.order.sercice;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.wb.order.dto.OrderDTO;

/**
 * 支付
 */
public interface PayService {
    //支付
    PayResponse create(OrderDTO orderDTO);

    //异步通知
    PayResponse notify(String notifyData);

    //退款
    RefundResponse refund(OrderDTO orderDTO);

}
