package com.wb.order.sercice.impl;

import com.wb.order.domain.OrderDetail;
import com.wb.order.dto.OrderDTO;
import com.wb.order.enums.OrderStatusEnum;
import com.wb.order.sercice.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String ORDER_ID = "1558400735385216227";

    private final String buyerOpenid = "fgdregdfdgd";

    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("慕课网");
        orderDTO.setBuyerName("师兄");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(buyerOpenid);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId(10001);
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("[创建订单] result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findById() {
        OrderDTO orderDTO = orderService.findById("1586765594485503573");
        System.out.println(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<OrderDTO> list = orderService.findList(pageRequest);
        for (OrderDTO orderDTO:list){
            System.out.println(orderDTO);
        }
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findById("1586765594485503573");
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findById("1586765594485503573");
        OrderDTO finish = orderService.finish(orderDTO);
        System.out.println(finish);
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findById("1586765594485503573");
        OrderDTO cancel = orderService.paid(orderDTO);
        System.out.println(cancel);
    }

    @Test
    public void findList1() {
        OrderDTO orderDTO = orderService.findById("1586765594485503573");
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<OrderDTO> list = orderService.findList(buyerOpenid, pageRequest);
        for (OrderDTO orderDTO1:list){
            System.out.println(orderDTO);
        }
    }
}