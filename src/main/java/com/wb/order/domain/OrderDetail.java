package com.wb.order.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 4507095367704078668L;

    @Id
    private String detailId;
    private String orderId;
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productIcon;
}
