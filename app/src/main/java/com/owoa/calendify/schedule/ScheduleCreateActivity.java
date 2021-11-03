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

public class ScheduleCreateActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView tv_result;
    private Button button;
    private EditText sample_Name , sample_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);

        spinner = (Spinner)findViewById(R.id.spinner);
        tv_result = (TextView)findViewById(R.id.tv_result);
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

        Intent i = new Intent(ScheduleCreateActivity.this,ScheduleCreateModel.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String name = sample_Name.getText().toString();
                String detail = sample_detail.getText().toString();
                String category = tv_result.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("detail",detail);
                bundle.putString("category",category);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

    }
}