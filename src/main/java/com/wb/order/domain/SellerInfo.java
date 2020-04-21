package com.wb.order.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家账号信息表
 */

@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;
}
