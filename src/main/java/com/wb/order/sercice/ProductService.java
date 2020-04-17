package com.wb.order.sercice;


import com.wb.order.domain.ProductCategory;
import com.wb.order.domain.ProductInfo;
import com.wb.order.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    ProductInfo findOne(Integer productId);

    //查询所有在架商品列表
    List<ProductInfo> findUpAll();
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);

    //加库存
    public void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    //新增、更新
    public void decreaseStock(List<CartDTO> cartDTOList);
}
