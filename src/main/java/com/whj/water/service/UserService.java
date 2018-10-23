package com.whj.water.service;

import com.whj.water.dto.Message;
import com.whj.water.model.User;
import com.whj.water.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Object updateUser(int userid,String name,String phone,String province,String city,String region,String address, String type,String wxname){
        if (!userRepository.existsById(userid)){
            return new Message(-1,"null user");
        }
        User user = userRepository.findById(userid).get();
        user.setName(name);
        user.setPhone(phone);
        user.setProvince(province);
        user.setCity(city);
        user.setRegion(region);
        user.setAddress(address);
        user.setType(type);
        user.setWxname(wxname);
        return userRepository.save(user);
    }


    public Object crateUser(String name,String phone,String province,String city,String region,String address, String type,String wxname){

        if (wxname!=null && userRepository.findFirstByWxname(wxname)!=null){
            return new Message(-1,"微信用户已经存在");
        }

        if (userRepository.findFirstByPhone(phone)!=null){
            return new Message(-1,"手机用户已经存在");
        }

        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setProvince(province);
        user.setCity(city);
        user.setRegion(region);
        user.setAddress(address);
        user.setType(type);
        user.setWxname(wxname);
        return userRepository.save(user);
    }

}
