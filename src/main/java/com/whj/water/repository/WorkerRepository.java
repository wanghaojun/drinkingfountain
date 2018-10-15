package com.whj.water.repository;

import com.whj.water.model.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker,Integer> {

    Worker findFirstByWxname(String wxname);

    Worker findFirstByCard(String card);



}
