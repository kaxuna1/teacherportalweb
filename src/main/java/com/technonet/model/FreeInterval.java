package com.technonet.model;

import org.joda.time.DateTime;

import java.sql.Time;
import java.util.Date;

/**
 * Created by vakhtanggelashvili on 3/15/17.
 */
public class FreeInterval {
    private DateTime start;
    private DateTime end;


    public FreeInterval(Time start, Time end,Date date) {
        this.start = new DateTime(date).withHourOfDay(start.getHours()).withMinuteOfHour(start.getMinutes());
        this.end = new DateTime(date).withHourOfDay(end.getHours()).withMinuteOfHour(end.getMinutes());
    }
    public FreeInterval(){

    }

    public Date getStart() {
        return start.toDate();
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public Date getEnd() {
        return end.toDate();
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }
}
