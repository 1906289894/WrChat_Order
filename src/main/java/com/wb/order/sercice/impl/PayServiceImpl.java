package com.wb.order.sercice.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.wb.order.dto.OrderDTO;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.sercice.OrderService;
import com.wb.order.sercice.PayService;
import com.wb.order.utils.JsonUtil;
import com.wb.order.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(orderDTO.getBuyerName());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //验证签名
        //支付的状态
        //支付金额
        //支付人（下单人）业务逻辑：是否为同一人


        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知, payResponse", JsonUtil.toJson(payResponse));

        //查询订单
        OrderDTO orderDTO = orderService.findById(payResponse.getOrderId());

        if (orderDTO == null) {
            log.error("[微信支付]异步通知， 订单不存在 , orderId={}",payResponse.getOrderId());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致
        if (!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("[微信支付]异步通知 ,订单金额不一致， orderId={} ,微信通知金额={}, 系统金额={}",payResponse.getOrderId(),
                    payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}",refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        return refundResponse;
    }


}
