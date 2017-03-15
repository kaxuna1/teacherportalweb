package com.technonet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.sql.Time;
import java.util.Date;
import java.util.List;

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

    public Date getStarting_time() {
        return start.toDate();
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public Date getEnding_time() {
        return end.toDate();
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public Duration getDuration(){
        return new Duration(this.getStarting_time().getTime(),this.getEnding_time().getTime());
    }
    public String getDurationString(){
        String durationString="";
        long hours=getDuration().getStandardHours();
        long minutes=(getDuration().getStandardMinutes()-(60*hours));
        return (hours>0?hours+" სთ. ":"")+minutes+" წთ";
    }

}
