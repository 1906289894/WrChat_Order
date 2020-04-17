package com.wb.order.dao;

import com.wb.order.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,Integer> {
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
