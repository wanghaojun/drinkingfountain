package com.whj.water.service;

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


import java.util.ArrayList;
import java.util.Iterator;

@Service
public class RecordInfoService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public Object findByWorkerid(int workerid){
        Iterable<Record> records = recordRepository.findByWorkeridOrderByTimeDesc(workerid);
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


}
