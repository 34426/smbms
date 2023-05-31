package com.igeek.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String userCode; //用户编码
    private String userName; //用户名称
    private String userPassword; //用户密码
    private int  gender; //性别
    private Date birthday; //出生日期
    private String phone; //电话
    private String address; //地址
    private long userRole; //用户角色
    private long createdBy; //创建者
    private Date creationDate; //创建时间
    private long modifyBy; //更新者
    private Date modifyDate; //更新时间

    private int age; //年龄
    private String userRoleName; //用户角色名称

    public int getAge() {
		/*long time = System.currentTimeMillis()-birthday.getTime();
		Integer age = Long.valueOf(time/365/24/60/60/1000).IntegerValue();*/
        Date date = new Date();
        int age = date.getYear()-birthday.getYear();
        return age;
    }

    public String getUserRoleName(){
        if (userRole==1){
            return "系统管理员";
        }else if (userRole==2){
            return "经理";
        }else if (userRole==3){
            return "普通员工";
        }
        return "";
    }
}
