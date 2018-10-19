package com.whj.water.controller;

import com.whj.water.dto.Message;
import com.whj.water.model.Reservation;
import com.whj.water.repository.ReservationRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
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

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Object create(int userid,int serviceid){
        return reservationService.create(userid,serviceid);
    }

    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    public Object pay(int reservationid){
        if (!reservationRepository.findById(reservationid).isPresent()){
            return new Message(-1,"null reservation");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        if (reservation.getIspay() != 0){
            return  new Message(-1,"pay error");
        }
        reservation.setIspay(1);
        return reservationRepository.save(reservation);
    }

    @RequestMapping(value = "/service",method = RequestMethod.POST)
    public Object service(int reservationid,int workerid){
        if (!reservationRepository.findById(reservationid).isPresent()){
            return new Message(-1,"null reservation");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        if (reservation.getIsservice() != 0){
            return new Message(-1,"service error");
        }
        reservation.setIsservice(1);
        recordService.create(reservation.getUserid(),workerid,reservation.getServiceId());
        return reservationRepository.save(reservation);
    }


    @RequestMapping("/findAll")
    public Object findAll(){
        return reservationRepository.findAll();
    }

    @RequestMapping("/findAllInfo")
    public Object findAllInfo(){
        return reservationService.getInfo(reservationRepository.findAll());
    }
    @RequestMapping("/findByUserid")
    public Object findByUserId(int userid){

        if (!userRepository.existsById(userid)){
            return new Message(0,"null user");
        }

        return reservationService.getInfo(reservationRepository
                .findByUseridOrderByTimeDesc(userid));
    }

    @RequestMapping("/findByServiceid")
    public Object findByServiceid(int serviceid){

        if (!serviceRepository.existsById(serviceid)){
            return new Message(0,"null service");
        }

        return reservationService.getInfo(reservationRepository
                .findByServiceIdOrderByTimeDesc(serviceid));
    }

    @RequestMapping("/findByUserPhone")
    public Object findByUserPhone(String phone){

        if (userRepository.findFirstByPhone(phone) == null){
           return new Message(0,"null user");
        }

        return reservationService.getInfo(reservationRepository
                .findByUseridOrderByTimeDesc(userRepository
                .findFirstByPhone(phone).getId()));
    }


}
