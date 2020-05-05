package com.wb.order.controller;

import com.wb.order.sercice.SecondKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟高并发下的redis分布式锁的实现
 */

@RequestMapping("/skill")
@RestController
@Slf4j
public class SecondKillController {

    @Autowired
    private SecondKillService secondKillService;

    /**
     * 查询秒杀商品的信息
     * @param productId
     * @return
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable() String productId){
        return secondKillService.querySecondKillProductInfo(productId);
    }

    /**
     * 秒杀系统
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable() String productId){
        log.info("@skill request, productId = "+productId);
        secondKillService.orderProductMockDiffUser(productId);
        return secondKillService.querySecondKillProductInfo(productId);
    }
}
