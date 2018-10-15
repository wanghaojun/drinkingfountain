package com.whj.water.controller;

import com.whj.water.dto.Message;
import com.whj.water.model.Record;
import com.whj.water.model.Service;
import com.whj.water.model.Worker;
import com.whj.water.repository.RecordRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import com.whj.water.service.RecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RecordInfoService recordInfoService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;



    @RequestMapping("/create")
    public Object create(int userid,int workerid,int serviceid){
        if (!userRepository.findById(userid).isPresent()){
            return new Message(-1,"null user");
        }

        if (!workerRepository.findById(workerid).isPresent()){
            return new Message(-1,"null worker");
        }

        if (!serviceRepository.findById(serviceid).isPresent()){
            return new Message(-1,"null service");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     // 北京
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Record record = new Record();
        record.setUserid(userid);
        record.setWorkerid(workerid);
        record.setServiceId(serviceid);
        Service service = serviceRepository.findById(serviceid).get();
        record.setServicename(service.getName());
        record.setPrice(service.getPrice());
        record.setTime(dateFormat.format(new Date()));
        record.setMonth(calendar.get(Calendar.MONTH)+1);
        record.setYear(calendar.get(Calendar.YEAR));
        record.setDay(calendar.get(Calendar.DATE));
        return recordRepository.save(record);


    }

    @RequestMapping("/findByWorkerid")
    public Object findRecordByWorkerid(int workerid){
        return recordInfoService.findByWorkerid(workerid);
    }

}
