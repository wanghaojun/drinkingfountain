package com.whj.water.controller;

import com.whj.water.dto.Message;
import com.whj.water.model.User;
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

    @RequestMapping("/findAll")
    public Object findAllUser(){return userRepository.findAll();}

    @RequestMapping("/findByWxname")
    public Object findUserByWxname(String wxname){
        return userRepository.findFirstByWxname(wxname);
    }

    @RequestMapping("/findByPhone")
    public Object findUserByPhone(String phone){
        return userRepository.findFirstByPhone(phone);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(String name,String phone,String province,String city,String region,String address, String type,String wxname){
        return userService.crateUser(name,phone,province,city,region,address,type,wxname);
    }

}
