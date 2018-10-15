package com.whj.water.service;

import com.whj.water.dto.RecordInfo;
import com.whj.water.model.Record;
import com.whj.water.model.Worker;
import com.whj.water.repository.RecordRepository;
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

    public void findByWokerid(int workerid){
        Iterable<Record> records = recordRepository.findByWorkeridOrderByTimeDesc(workerid);
        Worker worker = workerRepository.findById(workerid).get();
        Iterator<Record> recordIterator = records.iterator();
        ArrayList<RecordInfo> recordInfos = new ArrayList<>();
        Record record = new Record();
        while (recordIterator.hasNext()){
            record = recordIterator.next();
            RecordInfo recordInfo = new RecordInfo();
            recordInfo.id=record.getId();
            recordInfo.serviceId = record.getServiceId();
            recordInfo.userid = record.getUserid();
            recordInfo.workerid = record.getWorkerid();
            recordInfo.workername = worker.getName();
            recordInfo.workercard = worker.getCard();


        }


    }


}
