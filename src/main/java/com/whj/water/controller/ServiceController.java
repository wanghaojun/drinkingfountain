package com.whj.water.controller;

import com.whj.water.dto.Message;
import com.whj.water.model.Service;
import com.whj.water.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;


    /**
     * 新建一个服务
     * @param name 服务名字
     * @param price 服务价格
     * @param detail 服务详情
     * @return 服务
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(String name,String price,String detail){
        Service service = new Service();
        service.setName(name);
        service.setPrice(price);
        service.setDetail(detail);
        return  serviceRepository.save(service);
    }

    /**
     * 获取所有服务信息
     * @return 服务信息
     */
    @RequestMapping("/findAll")
    public Object findAll(){
        return serviceRepository.findAll();
    }

    /**
     * 删除服务
     * @param serviceid 服务id
     * @return 删除成功：1
     *         删除失败：-1
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object delete(int serviceid){
        if (serviceRepository.existsById(serviceid)){
            serviceRepository.deleteById(serviceid);
            return new Message(1,"delete success");
        }else {
            return new Message(-1,"null service");
        }
    }




}
