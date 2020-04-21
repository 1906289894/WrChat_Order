package com.wb.order.sercice;

import com.wb.order.domain.SellerInfo;

public interface SellerUserService {

    SellerInfo getSellerInfoByOpenId(String openId);
}
