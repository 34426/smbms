package com.igeek.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igeek.pojo.Provider;
import com.igeek.pojo.User;
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
public class ProviderController {

    @Autowired
    private ProviderService providerService;
    @RequestMapping("/jsp/queryProvider")
    public String queryProvider(String queryProCode, String queryProName, Model model){
        List<Provider> providerList = providerService.findByCodeAndName(queryProCode, queryProName);

        model.addAttribute("queryProCode",queryProCode);
        model.addAttribute("queryProName",queryProName);
        model.addAttribute("providerList",providerList);
        return "/jsp/providerlist";
    }

    @RequestMapping("/jsp/providerAdd")
    public String providerAdd(Model model,Provider provider, HttpSession session){
        provider.setId(1);
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        provider.setCreatedBy(userSession.getId());
        provider.setCreationDate(new Date(new java.util.Date().getTime()));
        int i = providerService.insertProvider(provider);
        if(i>0){
            model.addAttribute("message","添加成功");
        }else {
            model.addAttribute("message","添加失败");
        }

        return "/jsp/provideradd";
    }

    @RequestMapping("/jsp/deleteProvider")
    @ResponseBody
    public String deleteProvider(long proid) throws JsonProcessingException {
        int flag = providerService.deleteById(proid);
        Map<String, String> map = new HashMap<String,String>();
        if (flag>0){
            map.put("delResult","true");
        }else {
            map.put("delResult","false");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/jsp/viewProvider")
    public String viewProvider(long proid,Model model){
        Provider provider = providerService.findById(proid);
        model.addAttribute("provider",provider);
        return "/jsp/providerview";
    }

    @RequestMapping("/jsp/updateProvider")
    public String updateProvider(Provider provider,HttpSession session){
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        provider.setModifyBy(userSession.getId());
        provider.setModifyDate(new Date(new java.util.Date().getTime()));
        int i = providerService.updateProvider(provider);
        return "redirect:/jsp/queryProvider";
    }

    @RequestMapping("/jsp/toUpdateProvider")
    public String toUpdateProvider(long proid,Model model){
        Provider provider = providerService.findById(proid);
        model.addAttribute("provider",provider);
        return "/jsp/providermodify";
    }
}
