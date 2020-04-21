package com.wb.order.sercice.impl;

import com.wb.order.dao.SellerInfoRepository;
import com.wb.order.domain.SellerInfo;
import com.wb.order.sercice.SellerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应该是微信第三方授权，此处通过数据库查找代替
 */

@Service
public class SellerUserServiceImpl implements SellerUserService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo getSellerInfoByOpenId(String openId) {

        return sellerInfoRepository.findByOpenid(openId);
    }
}
