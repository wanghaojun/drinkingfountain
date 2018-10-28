package com.whj.water;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest
public class DateTest {

    @Test
    public void testdate(){
        System.setProperty("user.timezone","GMT +08");
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.AM));
        System.out.println(System.currentTimeMillis());
    }



}



