package com.whj.water.controller;

import com.whj.water.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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

}
