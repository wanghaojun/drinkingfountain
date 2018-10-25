package com.whj.water.repository;

import com.whj.water.model.Record;
import org.springframework.data.repository.CrudRepository;


public interface RecordRepository extends CrudRepository<Record,Integer> {

      Iterable<Record> findByWorkeridOrderByTimeDesc(int workerid);
      Iterable<Record> findByUseridOrderByTimeDesc(int userid);

}
