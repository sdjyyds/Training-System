package com.example.system.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的product表对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /**
     * 产品唯一 ID
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品价格
     */
    private BigDecimal price;

    /**
     * 产品简明介绍
     */
    private String shortDescription;

    /**
     * 产品图片链接
     */
    private String imageUrl;

    /**
     * 产品创建时间
     */
    private Date createdAt;

    /**
     * 产品详细介绍
     */
    private String longDescription;
}