package com.whj.water.controller;

import com.whj.water.dto.Message;
import com.whj.water.model.Record;
import com.whj.water.model.Service;
import com.whj.water.repository.RecordRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import com.whj.water.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private RecordService recordService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;



    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(int userid,int workerid,int serviceid){
        return  recordService.create(userid,workerid,serviceid);
    }

    @RequestMapping("/findByWorkerid")
    public Object findRecordByWorkerid(int workerid){
        return recordService.findByWorkerid(workerid);
    }

}
