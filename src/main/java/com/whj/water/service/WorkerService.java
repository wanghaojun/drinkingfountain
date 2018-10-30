package com.whj.water.service;

import com.whj.water.dto.Message;
import com.whj.water.model.Worker;
import com.whj.water.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    public Object saveWorker(String name,String wxname,String card,String phone,String province,String city,String region){

        if (workerRepository.findFirstByWxname(wxname)!=null){
            return new Message(-1,"微信用户已经存在");
        }

        if (workerRepository.findFirstByPhone(phone)!=null){
            return new Message(-1,"手机用户已经存在");
        }

        if (workerRepository.findFirstByCard(card)!=null){
            return new Message(-1,"工号已经存在");
        }

        Worker worker = new Worker();
        worker.setName(name);
        worker.setWxname(wxname);
        worker.setCard(card);
        worker.setPhone(phone);
        worker.setProvince(province);
        worker.setCity(city);
        worker.setRegion(region);

        return  workerRepository.save(worker);
    }

    public Object updateWorker(int workerid,String name,String wxname,String phone,String province,String city,String region){
        if (workerRepository.findById(workerid).isPresent()){
         Worker worker = workerRepository.findById(workerid).get();
         worker.setName(name);
         worker.setWxname(wxname);
         worker.setPhone(phone);
         worker.setProvince(province);
         worker.setCity(city);
         worker.setRegion(region);
         return workerRepository.save(worker);
        }else {
            return new Message(-1,"can't find worker");
        }
    }


}
