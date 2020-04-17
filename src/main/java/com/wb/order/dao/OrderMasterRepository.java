package com.wb.order.dao;

import com.wb.order.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //查询某人的订单，带分页
    Page<OrderMaster> findByBuyerOpenid(String BuyerOpenid, Pageable pageable);
}
