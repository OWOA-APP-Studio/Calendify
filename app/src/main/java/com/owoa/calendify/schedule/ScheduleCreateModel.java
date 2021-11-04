package com.owoa.calendify.schedule;

import android.widget.TextView;

public class ScheduleCreateModel {
    Contract.Presenter presenter;
    public ScheduleCreateModel(Contract.Presenter presenter){
        this.presenter = presenter;
    }

    public static void saveData(TextView tv_result) {
    }

    public void saveData(String data){
        //처리 로직
        String name = data;
    }
}
