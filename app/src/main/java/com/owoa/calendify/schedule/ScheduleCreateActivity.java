package com.owoa.calendify.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.owoa.calendify.R;

public class ScheduleCreateActivity extends AppCompatActivity implements Contract.View {

    private Spinner spinner;
    private TextView tv_result;
    private Button button;
    private EditText sample_Name , sample_detail;
    private Contract.Presenter ScheduleCreatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);
        ScheduleCreatePresenter = new ScheduleCreatePresenter((Contract.View) this);
        init();
    }


    protected void init() {
        spinner = (Spinner) findViewById(R.id.spinner);
        tv_result = (TextView) findViewById(R.id.tv_result);
        button = (Button) findViewById(R.id.add_button);
        sample_Name = (EditText) findViewById(R.id.sample_Name);
        sample_detail = (EditText) findViewById(R.id.sample_Detail);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String name = sample_Name.getText().toString();
                String detail = sample_detail.getText().toString();
                String category = tv_result.getText().toString();
                ScheduleCreatePresenter.get(name,detail,category);
                ScheduleCreateModel.saveData(tv_result);
            }
        });
    }

    @Override
    public void showResult(String name, String detail, String category) {
        Toast.makeText(getApplicationContext(),(name + "\n" + detail + "\n" + category),Toast.LENGTH_LONG).show();
//        ((TextView)findViewById(R.id.title)).setText(answer + num2 + num3);
    }

}