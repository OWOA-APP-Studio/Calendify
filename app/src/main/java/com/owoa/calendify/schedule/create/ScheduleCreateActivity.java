package com.owoa.calendify.schedule.create;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.schedule.create.ScheduleCreateModel.REQUEST_SCHEDULE_CREATE_URL;

public class ScheduleCreateActivity extends AppCompatActivity implements Contract.View {
    SimpleDateFormat mFormat_year = new SimpleDateFormat("yyyy");
    SimpleDateFormat mFormat_month = new SimpleDateFormat("MM");
    SimpleDateFormat mFormat_day = new SimpleDateFormat("dd");

    SimpleDateFormat mFormat_hour = new SimpleDateFormat("hh");
    SimpleDateFormat mFormat_minute = new SimpleDateFormat("mm");

    Date date = new Date();
    private int week ,y=0, m=0, d=0, h=0, mi=0;

    private int year = Integer.parseInt(mFormat_year.format(date));
    private int month = Integer.parseInt(mFormat_month.format(date));
    private int day = Integer.parseInt(mFormat_day.format(date));

    private int hour = Integer.parseInt(mFormat_hour.format(date));
    private int minute = Integer.parseInt(mFormat_minute.format(date));

    private EditText title, description, location;

    private Spinner category_spinner;


    private LinearLayout startDateTime;
    private TextView start_date, start_time;

    private LinearLayout endDateTIme;
    private TextView end_date, end_time;

    private char repeatType = 'D';
    private TextView repeat_times;

    private RadioGroup repeatCycleRadioGroup;
    private Contract.Presenter ScheduleCreatePresenter;

    private Button add_button;
    private CheckBox alarmCheck;

    private String uid;
    private ArrayList categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);

        uid = getIntent().getStringExtra(getString(R.string.uid));
        categories = (ArrayList) getIntent().getSerializableExtra("categories");

        ScheduleCreatePresenter = new ScheduleCreatePresenter((Contract.View) this);

        category_spinner = (Spinner) findViewById(R.id.schedule_create_category_spinner);
        add_button = (Button) findViewById(R.id.add_button);

        startDateTime = findViewById(R.id.schedule_create_start_datetime);
        endDateTIme = findViewById(R.id.schedule_create_end_datetime);

        start_date = (TextView) findViewById(R.id.schedule_create_start_date);
        start_time = (TextView) findViewById(R.id.schedule_create_start_time);

        end_date = (TextView) findViewById(R.id.schedule_create_end_date);
        end_time = (TextView) findViewById(R.id.schedule_create_end_time);

        title = (EditText) findViewById(R.id.schedule_create_title);
        description = findViewById(R.id.schedule_create_description);
        location = (EditText) findViewById(R.id.schedule_create_location);

        repeatCycleRadioGroup = (RadioGroup) findViewById(R.id.schedule_create_cycle_radioGroup);
        repeat_times = (TextView) findViewById(R.id.schedule_create_cycle_repeat_times);
        repeatCycleRadioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        alarmCheck = findViewById(R.id.schedule_create_alarm_checkBox);

        initialize();
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i) {
                case R.id.schedule_create_radio_day:
                    repeat_times.setVisibility(View.INVISIBLE);
                    week = 0;
                    repeatType = 'D';
                    break;
                case R.id.schedule_create_radio_week:
                    repeat_times.setVisibility(View.VISIBLE);
                    repeatType = 'W';
                    break;
            }
        }
    };

    protected void initialize() {
        // 스피너 아이템 설정 //
        ArrayAdapter categoryAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, categories);
        category_spinner.setAdapter(categoryAdapter);

        //카테고리 선택//
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ScheduleCreateActivity.this, "아무것도 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }});

        // 추가 버튼 //
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                String title = ScheduleCreateActivity.this.title.getText().toString();
                String detail = description.getText().toString();
                String category = category_spinner.getSelectedItem().toString();
                String date = start_date.getText().toString();
                String time= start_time.getText().toString();
                String locations = location.getText().toString();

                String repeat = Character.toString(repeatType);
                ScheduleCreatePresenter.get(title,detail,category,repeat,week,date,time,locations);

                createSchedule();
                //finish();
            }
        });

        // 시작 날짜 & 시간 선택 버튼 //
        startDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(start_date, start_time);
            }
        });

        endDateTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(end_date, end_time);
            }
        });
    }

    // 날짜 & 시간 선택 //
    private void showDateTimePicker(TextView date, TextView time) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                mi = minute;
                time.setText(String.format("%d:%d:00", h,mi));
            }
        }, hour, minute, true);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                y = year;
                m = month+1;
                d = dayOfMonth;
                date.setText(String.format("%d-%d-%d", y,m,d));

                timePickerDialog.setMessage("시간");
                timePickerDialog.show();
            }

        },year, month-1, day);

        datePickerDialog.setMessage("날짜");
        datePickerDialog.show();
    }

    // Contract //
    @Override
    public void showResult(String name, String detail, String category, String repeat ,int week,String date,String time, String location) {
//        Toast.makeText(getApplicationContext(),("일정 추가 완료" + "\n"
//                +"일정명: " + name + "\n"
//                +"상세내용: " + detail + "\n"
//                +"카테고리 명: " + category + "\n"
//                +"반복종류: "+ repeat + " " + week +" 주"+ "\n"
//                +"날짜: " + date + "\n"
//                +"시간: " + time + "\n"
//                +"장소: " + location),Toast.LENGTH_LONG).show();
    }

    public void createSchedule() {
        StringRequest createCategoryRequest = new StringRequest(Request.Method.POST, REQUEST_SCHEDULE_CREATE_URL, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("success");
                switch (result.charAt(0)) {
                    case 'S' :
                        Toast.makeText(this, "일정을 생성했습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 'F' :
                    default:
                        Toast.makeText(this, "일정을 다시 확인하고 추가해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다." + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", uid);
                params.put("sch_ttl", title.getText().toString());
                params.put("sch_dsc", description.getText().toString());
                params.put("sch_ctg", category_spinner.getSelectedItem().toString());
                params.put("sch_lct", location.getText().toString());
                params.put("sch_stm", start_time.getText().toString());
                params.put("sch_etm", end_time.getText().toString());
                params.put("sch_sdy", start_date.getText().toString());
                params.put("sch_edy", end_date.getText().toString());
                params.put("sch_slc_alr", String.valueOf(alarmCheck.isChecked() ? 1 : -1));
                params.put("sch_slc_typ", String.valueOf(repeatType));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(createCategoryRequest);
    }
}