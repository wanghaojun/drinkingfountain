package com.whj.water.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userid;
    private int workerid;
    private String workername;
    private String workercard;
    private int year;
    private int month;
    private int day;
    private String time;

    private int serviceId;

    public Record() {
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getWorkername() {
        return workername;
    }

    public void setWorkername(String workername) {
        this.workername = workername;
    }

    public String getWorkercard() {
        return workercard;
    }

    public void setWorkercard(String workercard) {
        this.workercard = workercard;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getWorkerid() {
        return workerid;
    }

    public void setWorkerid(int workerid) {
        this.workerid = workerid;
    }
}
