package com.igeek.mapper;

import com.igeek.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    public Bill findById(@Param("id") long id);

    public List<Bill> findByProductNameAndProviderIdAndIsPayment(@Param("productName") String productName,@Param("providerId") long providerId,@Param("isPayment") int isPayment);

    public int insertBill(Bill bill);

    public int deleteBill(@Param("id") long id);

    public int updateBill(Bill bill);
}
