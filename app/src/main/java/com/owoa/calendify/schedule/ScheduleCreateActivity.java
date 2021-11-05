package com.owoa.calendify.schedule;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.owoa.calendify.R;

public class ScheduleCreateActivity extends AppCompatActivity implements Contract.View {

    private Spinner spinner;
    private TextView tv_result;
    private Button add_button, date_button, time_button;
    private RadioButton radioButton_day,radioButton_week;
    private RadioGroup radioGroup;
    private EditText sample_Name , sample_detail;
    private Contract.Presenter ScheduleCreatePresenter;
    private String repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);
        ScheduleCreatePresenter = new ScheduleCreatePresenter((Contract.View) this);
        spinner = (Spinner) findViewById(R.id.spinner);
        tv_result = (TextView) findViewById(R.id.tv_result);
        add_button = (Button) findViewById(R.id.add_button);
        sample_Name = (EditText) findViewById(R.id.sample_Name);
        sample_detail = (EditText) findViewById(R.id.sample_Detail);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton_day = findViewById(R.id.radioButton_day);
        radioButton_week = findViewById(R.id.radioButton_week);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        init();
    }


    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.radioButton_day){
                repeat = radioButton_day.getText().toString();
            } else if(i == R.id.radioButton_week){
                repeat = radioButton_week.getText().toString();
                //
            }
        }
    };



    protected void init() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String name = sample_Name.getText().toString();
                String detail = sample_detail.getText().toString();
                String category = tv_result.getText().toString();
                ScheduleCreatePresenter.get(name,detail,category,repeat);
                ScheduleCreateModel.saveData(tv_result);
            }
        });

    }

    @Override
    public void showResult(String name, String detail, String category ,String repeat) {
        Toast.makeText(getApplicationContext(),("일정 추가 완료" + "\n"+ name + "\n" + detail + "\n" + category + "\n" + repeat),Toast.LENGTH_LONG).show();
//        ((TextView)findViewById(R.id.title)).setText(answer + num2 + num3);
    }

}