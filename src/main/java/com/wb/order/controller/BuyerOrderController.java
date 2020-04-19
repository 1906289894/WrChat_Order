package com.wb.order.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.wb.order.converter.OrderFrom2OrderDTOConverter;
import com.wb.order.dto.OrderDTO;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.form.OrderForm;
import com.wb.order.sercice.BuyerService;
import com.wb.order.sercice.OrderService;
import com.wb.order.utils.ResultVOUtils;
import com.wb.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //表单校验
        if (bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderFrom2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY_ERROR);
        }

        OrderDTO result = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVOUtils.success(map);
    }

    //带分页的订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0")Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size){
        //1.参数校验
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单] 查询订单列表错误, openid = {}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //查询列表
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> list = orderService.findList(openid, pageRequest);

        //返回结果
        return ResultVOUtils.success(list.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<List<OrderDTO>> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderid") String orderid){
        //参数校验
        if (StringUtils.isEmpty(openid)) {
            log.error("[订单详情] 查询订单详情错误，openid={}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(orderid)) {
            log.error("[订单详情] 查询订单详情错误，orderid={}",orderid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        //查询订单详情
        //注意安全问题，因任何人都可以访问其他人的订单
        OrderDTO orderOne = buyerService.findOrderOne(openid, orderid);
        return ResultVOUtils.success(orderOne);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderid") String orderid){
        //参数校验
        if (StringUtils.isEmpty(openid)) {
            log.error("[订单详情] 查询订单详情错误，openid={}",openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        if (StringUtils.isEmpty(orderid)) {
            log.error("[订单详情] 查询订单详情错误，orderid={}",orderid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        //注意安全问题，因任何人都可以访问其他人的订单
        buyerService.cancelOneOrder(openid,orderid);
        //如果取消订单出现异常，则会直接抛出异常，所以最后一句可以表示成功
        return ResultVOUtils.success();
    }

}
