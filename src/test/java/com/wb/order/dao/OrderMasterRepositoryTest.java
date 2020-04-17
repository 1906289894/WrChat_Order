package com.wb.order.dao;

import com.wb.order.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12454654");
        orderMaster.setBuyerName("向西欧你");
        orderMaster.setBuyerPhone("1433434343");
        orderMaster.setBuyerAddress("mukw");
        orderMaster.setBuyerOpenid("3439322");
        orderMaster.setOrderAmount(new BigDecimal(3.4));
        OrderMaster o1 = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(o1);
    }

    @Test
    public void findByBuyerOpenid() {
        String openId = "3439322";
        PageRequest request = PageRequest.of(0,2);
        Page<OrderMaster> byBuyerOpenid = orderMasterRepository.findByBuyerOpenid(openId, request);
        Assert.assertNotNull(byBuyerOpenid);
    }
}