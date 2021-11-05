package com.owoa.calendify.schedule;

public interface Contract {
    interface View{
        void showResult(String name, String detail, String category, int week);      //값을 보여줄 View 메소드 선언
    }
    interface Presenter{
        void get( String name, String detail, String category, int week);  //결과 값 구하기 위한 메소드 선언
    }

}
