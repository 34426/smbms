package com.igeek.service;

import com.igeek.mapper.BillMapper;
import com.igeek.mapper.ProviderMapper;
import com.igeek.pojo.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ProviderMapper providerMapper;

    public Bill findById(long id){
        Bill bill = billMapper.findById(id);
        bill.setProviderName(providerMapper.findById(bill.getProviderId()).getProName());
        return bill;
    }

    public List<Bill> findByProductNameAndProviderIdAndIsPayment(String productName,long providerId,int isPayment){
        List<Bill> billList = billMapper.findByProductNameAndProviderIdAndIsPayment(productName, providerId, isPayment);
        for (Bill bill : billList) {
            bill.setProviderName(providerMapper.findById(bill.getProviderId()).getProName());
        }
        return billList;
    }

    public int insertBill(Bill bill){
        return billMapper.insertBill(bill);
    }

    public int deleteBill(long id){
        return billMapper.deleteBill(id);
    }

    public int updateBill(Bill bill){
        return billMapper.updateBill(bill);
    }
}
