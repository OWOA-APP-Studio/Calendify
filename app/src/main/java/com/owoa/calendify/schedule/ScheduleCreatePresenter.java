package com.owoa.calendify.schedule;

public class ScheduleCreatePresenter implements Contract.Presenter {
    Contract.View view;
    ScheduleCreateModel scheduleCreateModel;
    public ScheduleCreatePresenter(Contract.View view){
        this.view = view;                   //Activty View정보 가져와 통신
        scheduleCreateModel = new ScheduleCreateModel(this);    //Model 객체 생성
    }

    //Presenter를 상속하고 addNum 구현
    @Override
    public void get(String name, String detail, String category ,String repeat) {
        view.showResult(name,detail,category,repeat);
    }
}
