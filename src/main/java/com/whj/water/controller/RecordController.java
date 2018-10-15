package com.whj.water.controller;

import com.whj.water.model.Record;
import com.whj.water.model.Worker;
import com.whj.water.repository.RecordRepository;
import com.whj.water.repository.WorkerRepository;
import org.omg.CORBA.OBJ_ADAPTER;
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



    @RequestMapping("/create")
    public Object create(int userid,int workerid,int serviceid){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     // 北京
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Worker worker =  workerRepository.findById(workerid).get();
        Record record = new Record();
        record.setUserid(userid);
        record.setWorkerid(workerid);
        record.setServiceId(serviceid);
        record.setTime(dateFormat.format(new Date()));
        record.setWorkername(worker.getName());
        record.setWorkercard(worker.getCard());
        record.setMonth(calendar.get(Calendar.MONTH)+1);
        record.setYear(calendar.get(Calendar.YEAR));
        record.setDay(calendar.get(Calendar.DATE));
        return recordRepository.save(record);


    }


    @RequestMapping("/findByWokerid")
    public Object findRecordByWorkerid(int workerid){
        return recordRepository.findByWorkeridOrderByTimeDesc(workerid);
    }

}
