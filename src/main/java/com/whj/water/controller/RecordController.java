package com.whj.water.controller;

import com.whj.water.repository.RecordRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import com.whj.water.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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


    /**
     * 创建一条服务记录
     * @param userid 用户id
     * @param workerid 工人工号
     * @param serviceid 服务id
     * @return 服务记录
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(int userid,int workerid,int serviceid){
        return  recordService.create(userid,workerid,serviceid);
    }

    /**
     * 获取某工人的服务记录
     * @param workerid 工人工号
     * @return 服务记录
     */
    @RequestMapping("/findByWorkerid")
    public Object findRecordByWorkerid(int workerid){
        return recordService.findByWorkerid(workerid);
    }

    /**
     * 获取某用户的服务记录
     * @param userid 用户id
     * @return 服务信息
     */
    @RequestMapping("/findByUserid")
    public Object findByUserid(int userid){
        return recordService.findByUserid(userid);
    }

}
