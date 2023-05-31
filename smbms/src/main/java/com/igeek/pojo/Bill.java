package com.igeek.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private long id;
    private String billCode; //账单编码
    private String productName; //商品名称
    private String productDesc; //商品描述
    private String productUnit; //商品单位
    private BigDecimal productCount; //商品数量
    private BigDecimal totalPrice; //总金额
    private int isPayment; //是否支付
    private long createdBy; //创建者
    private Date creationDate; //创建时间
    private long modifyBy; //更新者
    private Date modifyDate; //更新时间
    private long providerId; //供应商ID
    private String providerName; //提供者的姓名
}
