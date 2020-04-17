package com.wb.order.dao;

import com.wb.order.domain.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(10001);
        productInfo.setProductName("过桥米线");
        productInfo.setProductPrice(new BigDecimal(14.4));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("过桥米线，体验打破砂锅问到底的酸爽");
        productInfo.setProductIcon("http://xx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus(){
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        for (ProductInfo productInfo:productInfoList){
            System.out.println(productInfo.toString());
        }
    }
}