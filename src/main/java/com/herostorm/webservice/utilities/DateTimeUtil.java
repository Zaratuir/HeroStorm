package com.herostorm.webservice.utilities;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateTimeUtil {
    public DateTimeUtil(){

    }

    public long getNow(){
        return new Date().getTime();
    }

    public long get90Days(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 90);
        return cal.getTimeInMillis();
    }
}
