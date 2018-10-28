package com.whj.water.controller;

import com.whj.water.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping("/statistics")
    public Object statistics(int userid){
        return statisticService.getStatisticInfo(userid);
    }
}
