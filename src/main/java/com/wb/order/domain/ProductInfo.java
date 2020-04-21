package com.wb.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wb.order.enums.ProductStatusEnum;
import com.wb.order.utils.EnumUtils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class ProductInfo {
    @Id
    private Integer productId;

    //名字
    private String productName;

    //单价
    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    //描述
    private String productDescription;

    //小图
    private String productIcon;

    //状态
    private Integer productStatus;

    //类别编号
    private Integer categoryType;

    //创建时间
    private Date createTime = new Date();

    //修改时间
    private Date updateTime = new Date();

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtils.getByCode(productStatus,ProductStatusEnum.class);
    }
}
