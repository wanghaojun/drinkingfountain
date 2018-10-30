package com.whj.water.controller;

import com.whj.water.dto.Message;
import com.whj.water.model.Reservation;
import com.whj.water.model.Worker;
import com.whj.water.repository.ReservationRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import com.whj.water.service.RecordService;
import com.whj.water.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RecordService recordService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkerRepository workerRepository;

    /**
     * 新建预约记录
     * @param userid 用户id
     * @param serviceid 服务id
     * @return 预约记录
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(int userid,int serviceid){
        return reservationService.create(userid,serviceid);
    }

    /**
     * 获取预约人数
     * @return 预约人数
     */
    @RequestMapping(value = "/count")
    public Object count(){
        return reservationService.count();
    }

    /**
     * 确认支付某次服务
     * @param reservationid 预约id
     * @return 确认支付成功：返回预约信息
     *          失败：-1，失败原因
     */
    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public Object pay(int reservationid){
        return reservationService.pay(reservationid);
    }

    /**
     * 确认完成某次服务
     * @param reservationid 预约id
     * @param workerid 工人工号
     * @return 确认服务成功：返回预约信息
     *         失败：-1，失败原因
     */
    @RequestMapping(value = "/service",method = RequestMethod.POST)
    public Object service(int reservationid,int workerid){
       return reservationService.service(reservationid,workerid);
    }


    /**
     * 获取所有预约记录
     * @return 预约记录
     */
    @RequestMapping("/findAll")
    public Object findAll(){
        return reservationRepository.findAll();
    }

    /**
     * 获取预约记录及详细信息
     * @return 预约的详细信息
     */
    @RequestMapping("/findAllInfo")
    public Object findAllInfo(){
        return reservationService.getInfo(reservationRepository.findAll());
    }

    /**
     * 获取某用户的详细预约信息
     * @param userid 用户id
     * @return 预约详细信息
     */
    @RequestMapping("/findByUserid")
    public Object findByUserId(int userid){

        if (!userRepository.existsById(userid)){
            return new Message(0,"null user");
        }

        return reservationService.getInfo(reservationRepository
                .findByUseridOrderByTimeDesc(userid));
    }

    /**
     * 获取某服务的详细预约信息
     * @param serviceid 服务id
     * @return 预约详细信息
     */
    @RequestMapping("/findByServiceid")
    public Object findByServiceid(int serviceid){

        if (!serviceRepository.existsById(serviceid)){
            return new Message(0,"null service");
        }

        return reservationService.getInfo(reservationRepository
                .findByServiceIdOrderByTimeDesc(serviceid));
    }

    /**
     * 通过用户手机查找某用户的详细预约信息
     * @param phone 手机号
     * @return 预约详细信息
     */
    @RequestMapping("/findByUserPhone")
    public Object findByUserPhone(String phone){

        if (userRepository.findFirstByPhone(phone) == null){
           return new Message(0,"null user");
        }

        return reservationService.getInfo(reservationRepository
                .findByUseridOrderByTimeDesc(userRepository
                .findFirstByPhone(phone).getId()));
    }

    @RequestMapping(value = "/reserve",method = RequestMethod.POST)
    public Object reserve(int reservationid,String year,String month,String day,String hour){
        return reservationService.reserve(reservationid,year,month,day,hour);
    }

    @RequestMapping(value = "/getbyserviceanduser")
    public Object getByServiceAndUser(int serviceid,int userid){
        return reservationService.getInfo(reservationRepository.findByServiceIdAndUseridOrderByTimeDesc(serviceid,userid));
    }

    @RequestMapping(value = "/setworker",method = RequestMethod.POST)
    public Object setWorker(int reservationid,String workercard){
        return reservationService.setWorker(reservationid,workercard);
    }

    @RequestMapping(value = "/findbyworkercard",method = RequestMethod.GET)
    public Object findByWorkerCard(String workercard){
        return reservationService.getInfo(reservationRepository.findByWorkercardOrderByTimeDesc(workercard));
    }

    @RequestMapping(value = "/findundistribution")
    public Object findUnDistribution(){
        return reservationService.getInfo(reservationRepository.findByDistributionOrderByTimeDesc(false));
    }

    @RequestMapping(value = "/setundistribution")
    public Object setUnDistribution(int reservationid){
        return reservationService.unDistribution(reservationid);
    }

}
