package com.whj.water.repository;

import com.whj.water.model.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,Integer> {
    Iterable<Reservation> findByUseridOrderByTimeDesc(int userid);

    Iterable<Reservation> findByServiceIdOrderByTimeDesc(int serviceid);

}
