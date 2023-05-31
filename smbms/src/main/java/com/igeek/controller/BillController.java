package com.igeek.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igeek.pojo.Bill;
import com.igeek.pojo.Provider;
import com.igeek.pojo.User;
import com.igeek.service.BillService;
import com.igeek.service.ProviderService;
import com.igeek.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private ProviderService providerService;

    @RequestMapping("/jsp/queryBill")
    public String queryBill(Model model,String queryProductName, Integer queryProviderId, Integer queryIsPayment){

        if (queryProviderId==null){
            queryProviderId = 0;
        }
        if (queryIsPayment == null){
            queryIsPayment = 0;
        }
        List<Provider> providerList = providerService.findAll();
        List<Bill> billList = billService.findByProductNameAndProviderIdAndIsPayment(queryProductName, queryProviderId, queryIsPayment);
        model.addAttribute("queryProductName",queryProductName);
        model.addAttribute("queryProviderId",queryProviderId);
        model.addAttribute("queryIsPayment",queryIsPayment);
        model.addAttribute("billList",billList);
        model.addAttribute("providerList",providerList);
        return "/jsp/billlist";
    }

    @RequestMapping("/jsp/toBillAdd")
    public String toBillAdd(Model model){
        List<Provider> providerList = providerService.findAll();
        model.addAttribute("providerList",providerList);
        return "/jsp/billadd";
    }

    @RequestMapping("/jsp/billAdd")
    public String billAdd(Bill bill, HttpSession session){
        bill.setId(0);
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        bill.setCreatedBy(userSession.getId());
        bill.setCreationDate(new Date(new java.util.Date().getTime()));
        billService.insertBill(bill);
        return "redirect:/jsp/queryBill";
    }

    @RequestMapping("/jsp/deleteBill")
    @ResponseBody
    public String deleteBill(long billid) throws JsonProcessingException {
        Map<String, String> map = new HashMap<String, String>();
        if (billService.deleteBill(billid)>0){
            map.put("delResult","true");
        }else {
            map.put("delResult","false");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/jsp/viewBill")
    public String viewBill(long billid,Model model){
        Bill b = billService.findById(billid);
        model.addAttribute("bill",b);
        return "/jsp/billview";
    }

    @RequestMapping("/jsp/toUpdateBill")
    public String toUpdateBill(long billid,Model model){
        Bill b = billService.findById(billid);
        model.addAttribute("bill",b);
        List<Provider> providerList = providerService.findAll();
        model.addAttribute("providerList",providerList);
        return "/jsp/billmodify";
    }

    @RequestMapping("/jsp/updateBill")
    public String updateBill(Bill bill,Model model,HttpSession session){
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        bill.setModifyBy(userSession.getId());
        bill.setModifyDate(new Date(new java.util.Date().getTime()));

        int i = billService.updateBill(bill);
        if (i>0){
            model.addAttribute("message","修改成功");
        }else {
            model.addAttribute("message","修改失败");
        }

        return "/jsp/billmodify";
    }
}
