package com.wb.order.sercice;

public interface SecondKillService {

    String querySecondKillProductInfo(String productId);

    void orderProductMockDiffUser(String productId);
}
