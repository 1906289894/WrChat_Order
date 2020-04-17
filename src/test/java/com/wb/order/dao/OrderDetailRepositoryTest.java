package com.wb.order.dao;

import com.wb.order.domain.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;


    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("3439322");
        orderDetail.setDetailId("24502405445");
        orderDetail.setProductIcon("http://www.baidu.com");
        orderDetail.setProductId(1578751963);
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(2.5));
        orderDetail.setProductQuantity(3);
        OrderDetail result = orderDetailRepository.save(orderDetail);
    }


    @Test
    public void findByOrderId() {
        String orderId = "3439322";
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        Assert.assertNotNull("空",orderDetails.size());
    }
}