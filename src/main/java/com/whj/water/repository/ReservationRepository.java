package com.whj.water.repository;

import com.whj.water.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReservationRepository extends CrudRepository<Reservation,Integer> {
    Iterable<Reservation> findByUseridOrderByTimeDesc(int userid);

    Iterable<Reservation> findByServiceIdOrderByTimeDesc(int serviceid);

    ArrayList<Reservation> findByServiceIdAndUseridOrderByTimeDesc(int serviceid,int userid);

    Reservation findFirstByServiceIdAndUserid(int serviceid,int userid);

}
