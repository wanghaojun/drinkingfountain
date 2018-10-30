package com.whj.water.service;

import com.whj.water.dto.Message;
import com.whj.water.dto.ReservationInfo;
import com.whj.water.model.Reservation;
import com.whj.water.model.User;
import com.whj.water.model.Worker;
import com.whj.water.repository.ReservationRepository;
import com.whj.water.repository.ServiceRepository;
import com.whj.water.repository.UserRepository;
import com.whj.water.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RecordService recordService;

    public Object count(){
        return reservationRepository.count();
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

    public Object create(int userid,int serviceid){

        if (!userRepository.findById(userid).isPresent()){
            return new Message(-1,"null user");
        }

        if (!serviceRepository.findById(serviceid).isPresent()){
            return new Message(-1,"null service");
        }

        if (getIsReservation(serviceid,userid)){
            return new Message(-1,"don't reseve again");
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     // 北京
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        Reservation reservation = new Reservation();
        reservation.setUserid(userid);
        reservation.setServiceId(serviceid);

        reservation.setTime(dateFormat.format(new Date()));
        reservation.setYear(calendar.get(Calendar.YEAR));
        reservation.setMonth(calendar.get(Calendar.MONTH)+1);
        reservation.setDay(calendar.get(Calendar.DATE));

        return reservationRepository.save(reservation);
    }

    public Object getInfo(Iterable<Reservation> reservations){
        Iterator<Reservation> reservationIterator = reservations.iterator();
        ArrayList<ReservationInfo> reservationInfos = new ArrayList<>();
        while (reservationIterator.hasNext()){
            Reservation reservation = reservationIterator.next();
            User user = userRepository.findById(reservation.getUserid()).get();
            com.whj.water.model.Service service = serviceRepository.findById(reservation.getServiceId()).get();
            ReservationInfo reservationInfo = new ReservationInfo();

            reservationInfo.id = reservation.getId();

            reservationInfo.userId = reservation.getUserid();
            reservationInfo.wxname = user.getWxname();
            reservationInfo.name = user.getName();
            reservationInfo.phone = user.getPhone();
            reservationInfo.type = user.getType();
            reservationInfo.province = user.getProvince();
            reservationInfo.city = user.getCity();
            reservationInfo.region = user.getRegion();
            reservationInfo.address  = user.getAddress();

            reservationInfo.serviceid = service.getId();
            reservationInfo.servicename = service.getName();
            reservationInfo.serviceprice = service.getPrice();

            reservationInfo.time = reservation.getTime();
            reservationInfo.year = reservation.getYear();
            reservationInfo.month = reservation.getMonth();
            reservationInfo.day = reservation.getDay();

            reservationInfo.ispay = reservation.getIspay();
            reservationInfo.isservice = reservation.getIsservice();

            reservationInfos.add(reservationInfo);
        }

        return reservationInfos;
    }

    public Object reserve(int reservationid,String year,String month,String day,String hour){
        if (!reservationRepository.existsById(reservationid)){
            return new Message(-1,"null reservation");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        String time = year + '-' + month + '-' + day + '-' +hour;
        reservation.setReservationtime(time);
        return reservationRepository.save(reservation);
    }

    public Object pay(int reservationid){
        if (!reservationRepository.findById(reservationid).isPresent()){
            return new Message(-1,"null reservation");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        if (reservation.getIspay() != 0){
            return  new Message(-1,"don't pay again");
        }
        reservation.setIspay(1);
        if (reservation.getIsservice()==1){
            Worker worker = workerRepository.findFirstByCard(reservation.getWorkercard());
            recordService.create(reservation.getUserid(),worker.getId(),reservation.getServiceId());
        }
        return reservationRepository.save(reservation);
    }

    public Object service(int reservationid,int workerid){
        if (!reservationRepository.findById(reservationid).isPresent()){
            return new Message(-1,"null reservation");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        if (reservation.getIsservice() != 0){
            return new Message(-1,"don't service again");
        }
        reservation.setIsservice(1);
        if (reservation.getIspay()==1){
            recordService.create(reservation.getUserid(),workerid,reservation.getServiceId());
        }
        return reservationRepository.save(reservation);
    }

    public Object setWorker(int reservationid,String workercrad){
        if (!reservationRepository.findById(reservationid).isPresent()){
            return new Message(-1,"null reservation");
        }
        if (workerRepository.findFirstByCard(workercrad)==null){
            return new Message(-1,"null worker");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        reservation.setDistribution(true);
        reservation.setWorkercard(workercrad);
        return reservationRepository.save(reservation);
    }

    public Object unDistribution(int reservationid){
        if (!reservationRepository.findById(reservationid).isPresent()){
            return new Message(-1,"null reservation");
        }
        Reservation reservation = reservationRepository.findById(reservationid).get();
        reservation.setDistribution(false);
        reservation.setWorkercard(null);
        return reservationRepository.save(reservation);
    }

}
