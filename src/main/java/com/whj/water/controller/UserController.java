package com.whj.water.controller;

import com.whj.water.repository.UserRepository;
import com.whj.water.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * 查询所有用户信息
     * @return 用户信息
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public Object findAllUser(){return userRepository.findAll();}

    /**
     * 通过wxname查询用户信息
     * @param wxname 微信号
     * @return 用户信息
     */
    @RequestMapping("/findByWxname")
    public Object findUserByWxname(String wxname){
        return userRepository.findFirstByWxname(wxname);
    }

    /**
     * 通过手机号查询用户信息
     * @param phone 用户手机号
     * @return 用户信息
     */
    @RequestMapping("/findByPhone")
    public Object findUserByPhone(String phone){
        return userRepository.findFirstByPhone(phone);
    }

    /**
     * 新建一名用户
     * @param name 用户姓名
     * @param phone 手机号
     * @param province 省份
     * @param city 城市
     * @param region 地区
     * @param address 详细地址
     * @param type 净水器类型
     * @param wxname 微信名
     * @return 用户信息
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(String name,String phone,String province,String city,String region,String address, String type,String wxname){
        return userService.crateUser(name,phone,province,city,region,address,type,wxname);
    }

}
