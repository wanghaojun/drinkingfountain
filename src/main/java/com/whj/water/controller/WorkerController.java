package com.whj.water.controller;

import com.whj.water.model.User;
import com.whj.water.model.Worker;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    public Object saveUser(String name,String phone,String province,String city,String region,String address, String type,String wxname){
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setProvice(province);
        user.setCity(city);
        user.setRegion(region);
        user.setAddress(address);
        user.setType(type);
        user.setWxname(wxname);
        return userRepository.save(user);
    }

    @RequestMapping(value = "/saveWorker",method = RequestMethod.POST)
    public Object saveWorker(String name,String wxname,String card,String phone,String province,String city,String region){
        Worker worker = new Worker();
        worker.setName(name);
        worker.setWxname(wxname);
        worker.setCard(card);
        worker.setPhone(phone);
        worker.setProvice(province);
        worker.setCity(city);
        worker.setRegion(region);

        return  workerRepository.save(worker);
    }

    @RequestMapping("/findAll")
    public Object findAllWorker(){return workerRepository.findAll();}

    @RequestMapping("/findByWxname")
    public Object findWorkerByWxname(String wxname){
         return workerRepository.findFirstByWxname(wxname);
    }




}
