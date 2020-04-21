package com.wb.order.controller;

import com.wb.order.dto.OrderDTO;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.sercice.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.time.OffsetDateTime;
import java.util.Map;

/**
 * 卖家端订单controller
 */

@RequestMapping("/seller/order")
@Controller
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 后端的订单列表
     * @param page 当前第几页，默认为从第一页开始
     * @param size 每页有多少条记录，默认5条
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                             @RequestParam(value = "size" ,defaultValue = "5") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/order/list",map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId")String orderId,Map<String,Object> map){

        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e){
            log.error("[卖家端取消订单]发生异常");
            //返回公共错误页面
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");//将要跳转的页面
            return new ModelAndView("/common/error",map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");//将跳转页面
        return new ModelAndView("/common/success",map);
    }

    /**
     * 订单详情页面
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,Map<String,Object> map){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findById(orderId);
        }catch (SellException e){

            log.error("[卖家端订单详情] 发生异常, {}", map);
            //返回公共错误页面
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");//将要跳转的页面
            return new ModelAndView("/commom/error",map);
        }

        map.put("orderDTO",orderDTO);
        return new ModelAndView("/order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                                Map<String,Object> map){
        try {
            OrderDTO orderDTO = orderService.findById(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            log.error("[卖家端完结订单] 发生异常, {}",e);
            //返回公共错误页面
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
        }

        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }
}
