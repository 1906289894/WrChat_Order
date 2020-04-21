package com.wb.order.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFrom {

    private String productId;
    private String productName;
    private String productDescription;
    private String productIcon;
    private Integer categoryType;
    private BigDecimal productPrice;
    private Integer productStock;
}
