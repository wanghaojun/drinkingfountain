package com.whj.water.service;

import com.whj.water.dto.Message;
import com.whj.water.dto.RecordInfo;
import com.whj.water.model.Record;
import com.whj.water.model.User;
import com.whj.water.model.Worker;
import com.whj.water.repository.RecordRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecordService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;


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
        com.whj.water.model.Service service = serviceRepository.findById(serviceid).get();
        record.setServicename(service.getName());
        record.setPrice(service.getPrice());
        record.setTime(dateFormat.format(new Date()));
        record.setMonth(calendar.get(Calendar.MONTH)+1);
        record.setYear(calendar.get(Calendar.YEAR));
        record.setDay(calendar.get(Calendar.DATE));
        User user=userRepository.findById(userid).get();
        user.setLastservice(dateFormat.format(new Date()));
        userRepository.save(user);
        return recordRepository.save(record);
    }

    public Object findByWorkerid(int workerid){
        Iterable<Record> records = recordRepository.findByWorkeridOrderByTimeDesc(workerid);
        return getRecordInfo(records);
    }

    public Object findByUserid(int userid){
        Iterable<Record> records = recordRepository.findByUseridOrderByTimeDesc(userid);
        return getRecordInfo(records);
    }

    public  ArrayList<RecordInfo> getRecordInfo(Iterable<Record> records){

        Iterator<Record> recordIterator = records.iterator();
        ArrayList<RecordInfo> recordInfos = new ArrayList<>();
        Record record = new Record();
        while (recordIterator.hasNext()){
            record = recordIterator.next();
            RecordInfo recordInfo = new RecordInfo();
            recordInfo.id=record.getId();

            recordInfo.servicename = record.getServicename();
            recordInfo.serviceprice = record.getPrice();

            Worker worker = workerRepository.findById(record.getWorkerid()).get();
            recordInfo.workerid = record.getWorkerid();
            recordInfo.workername = worker.getName();
            recordInfo.workercard = worker.getCard();

            User user = userRepository.findById(record.getUserid()).get();
            recordInfo.userid = record.getUserid();
            recordInfo.username = user.getName();
            recordInfo.userphone = user.getPhone();
            recordInfo.useraddress = user.getAddress();
            recordInfo.userprovince = user.getProvince();
            recordInfo.usercity = user.getCity();
            recordInfo.userregion = user.getRegion();

            recordInfo.year = record.getYear();
            recordInfo.month = record.getMonth();
            recordInfo.day = record.getDay();
            recordInfo.time = record.getTime();

            recordInfos.add(recordInfo);

        }
        return recordInfos;
    }

    public Object getLastRecord(int userid){
        Iterator<Record> recordIterator = recordRepository.findByUseridOrderByTimeDesc(userid).iterator();
        if (recordIterator.hasNext()){
            Record record = recordIterator.next();
            return record;
        }else {
            return new Message(-1,"null record");
        }
    }


}
