package com.wb.order.controller;

import com.wb.order.sercice.SellerUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 卖家用户操作
 */
@Slf4j
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerUserService sellerUserService;


}
