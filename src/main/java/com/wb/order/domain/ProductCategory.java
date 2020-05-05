package com.wb.order.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = -3643911199039246860L;
    //类目id
    @Id //标注用于声明一个实体类的属性映射为数据库的主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    //类目名称
    private String categoryName;
    //类目编号
    private Integer categoryType;

    private Date createTime = new Date();

    private Date updateTime = new Date();

    public ProductCategory() {
    }
}
