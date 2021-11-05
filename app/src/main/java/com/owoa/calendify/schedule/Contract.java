package com.owoa.calendify.schedule;

import android.app.TimePickerDialog;
import android.widget.TimePicker;

public interface Contract {
    interface View{
        void showResult(String name, String detail, String category,String repeat ,int week,String date,String time);      //값을 보여줄 View 메소드 선언
    }
    interface Presenter{
        void get( String name, String detail, String category,String repeat, int week,String date,String time);

    }

}
