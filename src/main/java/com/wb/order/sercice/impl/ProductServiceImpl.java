package com.wb.order.sercice.impl;

import com.wb.order.dao.ProductInfoRepository;
import com.wb.order.domain.ProductCategory;
import com.wb.order.domain.ProductInfo;
import com.wb.order.dto.CartDTO;
import com.wb.order.enums.ProductStatusEnum;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.sercice.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(Integer productId) {
        Optional<ProductInfo> optional = repository.findById(productId);

        return optional.isPresent()?optional.get():null;
    }

    @Override
    public List<ProductInfo> findUpAll() {

        //return repository.findByProductStatus();
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        return byProductStatus;
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null){
                //商品不存在
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //减库存
            Integer resultStock = productInfo.getProductStock()-cartDTO.getProductQuantity();
            if (resultStock < 0 ){
                //库存不足
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }


            //保存库存
            productInfo.setProductStock(resultStock);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo offSale(String productIds) {
        //转换productId为Integer类型
        String[] split = productIds.split(",");
        String s = split[0]+split[1];
        Integer productId = Integer.valueOf(s);
        ProductInfo productInfo = null;
        try {
            Optional<ProductInfo> optional = repository.findById(productId);
            if (optional.isPresent()) {
                productInfo = optional.get();
            }
        }catch (NoSuchElementException e){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //判断商品状态
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新状态
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productIds) {
        //转换productId为Integer类型
        String[] split = productIds.split(",");
        String s = split[0]+split[1];
        Integer productId = Integer.valueOf(s);

        ProductInfo productInfo = null;
        try {
            Optional<ProductInfo> optional = repository.findById(productId);
            if (optional.isPresent()) {
                productInfo = optional.get();
            }
        }catch (NoSuchElementException e){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //判断商品状态
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新状态
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }


}
