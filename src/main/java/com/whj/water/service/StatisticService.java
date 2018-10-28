package com.whj.water.service;

import com.whj.water.dto.Message;
import com.whj.water.dto.StatisticsInfo;
import com.whj.water.model.Reservation;
import com.whj.water.repository.ReservationRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class StatisticService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private int getCount(int serviceid,int userid){
        return reservationRepository.findByServiceIdAndUseridOrderByTimeDesc(serviceid,userid).size();
    }

    private String getLastime(int serviceid,int userid){
        ArrayList<Reservation> reservations =  reservationRepository.findByServiceIdAndUseridOrderByTimeDesc(serviceid,userid);
        if (reservations.size()==0){
            return "0";
        }else {
            return reservations.get(0).getTime();
        }
    }

    private Boolean getIsReservation(int serviceid,int userid){
        ArrayList<Reservation> reservations =  reservationRepository.findByServiceIdAndUseridOrderByTimeDesc(serviceid,userid);
        if (reservations.size()==0){
            return false;
        }
        if (reservations.get(0).getIsservice()==0){
            return true;
        }else {
            return false;
        }
    }

    public Object getStatisticInfo(int userid){
        if (!userRepository.findById(userid).isPresent()){
            return new Message(-1,"null user");
        }
        Iterable<com.whj.water.model.Service> serviceIterable = serviceRepository.findAll();
        Iterator<com.whj.water.model.Service> serviceIterator = serviceIterable.iterator();
        ArrayList<StatisticsInfo> statisticsInfos = new ArrayList<>();
        while (serviceIterator.hasNext()){
            com.whj.water.model.Service service = serviceIterator.next();
            StatisticsInfo statisticsInfo = new StatisticsInfo();
            statisticsInfo.serviceid=service.getId();
            statisticsInfo.servicename=service.getName();
            statisticsInfo.serviceprice = service.getPrice();
            statisticsInfo.servicedetail = service.getDetail();
            statisticsInfo.userid = userid;
            statisticsInfo.count = getCount(service.getId(),userid);
            statisticsInfo.lasttime = getLastime(service.getId(),userid);
            statisticsInfo.isReservation = getIsReservation(service.getId(),userid);
            statisticsInfos.add(statisticsInfo);
        }
        return statisticsInfos;
    }

}
