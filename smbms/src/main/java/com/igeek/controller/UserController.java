package com.igeek.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igeek.pojo.Role;
import com.igeek.service.RoleService;
import com.igeek.utils.Constants;
import com.igeek.pojo.User;
import com.igeek.service.UserService;
import com.igeek.utils.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("login.do")
    public String login(String userCode , String userPassword, HttpSession session, HttpServletRequest request){
        User user = userService.login(userCode, userPassword);
        if (user!=null){
            session.setAttribute(Constants.USER_SESSION,user);
            return "redirect:/jsp/frame.jsp";
        }
        request.setAttribute("error","用户名或者密码错误");
        return "/login";
    }

    @RequestMapping("/jsp/logout.do")
    public String logout(HttpSession session){
        session.removeAttribute(Constants.USER_SESSION);
        return "redirect:/login.jsp";
    }

    @RequestMapping("/jsp/updatePassword")
    public String updatePassword(String newPassword,HttpSession session,HttpServletRequest request){
        System.out.println("newpassword=>"+newPassword);
        User attribute = (User) session.getAttribute(Constants.USER_SESSION);
        boolean flag = userService.updatePasswordById(request.getParameter("newPassword"), attribute.getId());
        if (flag==true){
            session.removeAttribute(Constants.USER_SESSION);
            return "redirect:/login.jsp";
        }else {
            request.setAttribute("message","修改密码失败");
            return "/jsp/pwdmodify";
        }
    }

    @RequestMapping("/jsp/verificationPassword")
    @ResponseBody
    public String verificationPassword(String oldpassword,HttpSession session) throws JsonProcessingException {
        User attribute = (User) session.getAttribute(Constants.USER_SESSION);
        Map<String,String> map = new HashMap<String,String>();

        if (attribute==null){
            map.put("result","sessionerror");
        }else if (oldpassword==null||oldpassword.length()==0){
            map.put("result","error");
        }else if (!attribute.getUserPassword().equals(oldpassword)){
            map.put("result","false");
        }
        map.put("result","true");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        return s;
    }

    @RequestMapping("/jsp/queryUser")
    public String queryUser(Model model,Integer queryUserRole,String queryname,Integer pageIndex){
        //1. 查询出列表
        //2. 查询出总人数
        //3. 查询出role列表
        //4. 分页的支持
        //5. 回写数据
        //6. 跳转页面
        if (queryUserRole==null){
            queryUserRole = 0;
        }
        if (pageIndex == null){
            pageIndex = 1;
        }
        int template = (pageIndex-1)*Constants.PAGE_SIZE;
        List<User> userList = userService.findUserByRoleIdAndUsernameAndPageLimit(queryUserRole, queryname, template, Constants.PAGE_SIZE);
        int totalCount = userService.findUserCountByRoleIdAndUsername(queryUserRole, queryname);
        List<Role> roleList = roleService.findAll();
        PageSupport pageSupport = new PageSupport();
        pageSupport.setPageSize(Constants.PAGE_SIZE);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalPageCount();


        model.addAttribute("queryUserName",queryname);
        model.addAttribute("roleList",roleList);
        model.addAttribute("queryUserRole",queryUserRole);
        model.addAttribute("userList",userList);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("currentPageNo","");
        model.addAttribute("totalPageCount",totalPageCount);
        model.addAttribute("currentPageNo",pageIndex);

        return "/jsp/userlist";
    }

    @RequestMapping("/jsp/AddUser")
    public String addUser(String userCode, String userName, String userPassword,
                          String ruserPassword, int gender, String birthday,
                          String phone, String address,long userRole,Model model,HttpSession session) throws ParseException {
        if (ruserPassword==null||userPassword==null||!userPassword.equals(ruserPassword)){
            model.addAttribute("message","输入异常,请重新输入");
            return "/jsp/useradd";
        }
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setGender(gender);
        //把字符串转换为日期对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = dateFormat.parse(birthday);
        user.setBirthday(parse);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(userRole);
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        user.setCreatedBy(userSession.getId());
        user.setCreationDate(new Date());
        userService.insertUser(user);
        System.out.println("======================>"+user);
        model.addAttribute("message","添加成功");
        return "/jsp/useradd";
    }


    @RequestMapping("/jsp/toUserAdd")
    public String toUserAdd(Model model){
        List<Role> all = roleService.findAll();
        model.addAttribute("roleList",all);
        return "/jsp/useradd";
    }

    //验证账户名是否一样
    @RequestMapping("/jsp/verifyAccount")
    @ResponseBody
    public String verifyAccount(String userCode) throws JsonProcessingException {
//        System.out.println("###################");
//        System.out.println(userCode);
//        System.out.println("#####################");
        Map<String, String> map = new HashMap<String, String>();
        if (userCode==null||userCode.equals("")){
            map.put("userCode","exist");
        }
        List<User> userList = userService.findAll();
        for (User user : userList) {
            if (user.getUserCode().equals(userCode)){
                map.put("userCode","exist");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/jsp/deleteUser")
    @ResponseBody
    public String deleteUser(long uid) throws JsonProcessingException {
        System.out.println("==========="+"进入方法了"+"===========");
        Map<String,String> map = new HashMap<String, String>();
        int i = userService.deleteById(uid);
        if (i<1){
            map.put("delResult","false");
        }else {
            map.put("delResult","true");
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/jsp/viewUser")
    public String viewUser(long uid,Model model){
        User user = userService.findById(uid);
        model.addAttribute("user",user);
        return "/jsp/userview";
    }

    @RequestMapping("/jsp/toUpdateUser")
    public String toUpdateUser(long uid,Model model){
        User user = userService.findById(uid);
        model.addAttribute("user",user);
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList",roleList);
        return "/jsp/usermodify";
    }

    @RequestMapping("/jsp/updateUser")
    public String updateUser(long uid,String userName,int gender,String birthday,
                             String phone,String address,long userRole,HttpSession session) throws ParseException {
        User user = new User();
        user.setId(uid);
        user.setUserName(userName);
        user.setGender(gender);
        //转换一下日期,因为前端不可以传日期对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = dateFormat.parse(birthday);
        user.setBirthday(parse);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(userRole);
        User userSession = (User) session.getAttribute(Constants.USER_SESSION);
        user.setModifyBy(userSession.getId());
        user.setModifyDate(new Date());
        userService.updateUser(user);
        return "redirect:/jsp/queryUser";
    }
}
