package com.owoa.calendify.schedule.update;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owoa.calendify.R;
import com.owoa.calendify.schedule.ScheduleModel;
import com.owoa.calendify.schedule.create.Contract;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.owoa.calendify.schedule.update.ScheduleUpdateModel.REQUEST_SCHEDULE_UPDATE_URL;


public class ScheduleUpdateActivity extends AppCompatActivity implements Contract.View {
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

    private Button update_button;
    private CheckBox alarmCheck;

    private String uid;
    private ArrayList categories;
    ScheduleModel scheduleModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_update);

        uid = getIntent().getStringExtra(getString(R.string.uid));
        categories = new ArrayList();
        Collections.addAll(categories, (String[]) getIntent().getSerializableExtra("categories"));

        scheduleModel = (ScheduleModel)getIntent().getSerializableExtra("model");

        category_spinner = (Spinner) findViewById(R.id.schedule_update_category_spinner);
        update_button = (Button) findViewById(R.id.update_button);

        startDateTime = findViewById(R.id.schedule_update_start_datetime);
        endDateTIme = findViewById(R.id.schedule_update_end_datetime);

        start_date = (TextView) findViewById(R.id.schedule_update_start_date);
        start_time = (TextView) findViewById(R.id.schedule_update_start_time);

        end_date = (TextView) findViewById(R.id.schedule_update_end_date);
        end_time = (TextView) findViewById(R.id.schedule_update_end_time);

        title = (EditText) findViewById(R.id.schedule_update_title);
        description = findViewById(R.id.schedule_update_description);
        location = (EditText) findViewById(R.id.schedule_update_location);

        repeatCycleRadioGroup = (RadioGroup) findViewById(R.id.schedule_update_cycle_radioGroup);
        repeat_times = (TextView) findViewById(R.id.schedule_update_cycle_repeat_times);
        repeatCycleRadioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        alarmCheck = findViewById(R.id.schedule_update_alarm_checkBox);

        initialize();
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            switch (i) {
                case R.id.schedule_update_radio_day:
                    repeat_times.setVisibility(View.INVISIBLE);
                    week = 0;
                    repeatType = 'D';
                    break;
                case R.id.schedule_update_radio_week:
                    repeat_times.setVisibility(View.VISIBLE);
                    repeatType = 'W';
                    break;
            }
        }
    };

    protected void initialize() {
        // 일정 정보 불러오기
        title.setText(scheduleModel.getTitle());

        start_date.setText(scheduleModel.getStartDay());
        start_time.setText(scheduleModel.getStartTime());

        end_date.setText(scheduleModel.getEndDay());
        end_time.setText(scheduleModel.getEndTime());

        location.setText(scheduleModel.getLocation());

        description.setText(scheduleModel.getDescription());

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
                Toast.makeText(ScheduleUpdateActivity.this, "아무것도 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }});

        // 추가 버튼 //
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                scheduleModel.setTitle(ScheduleUpdateActivity.this.title.getText().toString());
                scheduleModel.setDescription(description.getText().toString());
                scheduleModel.setCategory(category_spinner.getSelectedItem().toString());
                scheduleModel.setStartTime(start_time.getText().toString());
                scheduleModel.setEndTime(end_time.getText().toString());
                scheduleModel.setStartDay(start_date.getText().toString());
                scheduleModel.setEndDay(end_date.getText().toString());
                scheduleModel.setLocation(location.getText().toString());

                updateSchedule();
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

    public void updateSchedule() {
        StringRequest updateCategoryRequest = new StringRequest(Request.Method.POST, REQUEST_SCHEDULE_UPDATE_URL, response -> {
            try {
                Log.d("SUA-US", response);
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("success");
                switch (result.charAt(0)) {
                    case 'S' :
                        Toast.makeText(this, "일정을 수정했습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 'F' :
                    default:
                        Toast.makeText(this, "일정을 다시 확인하고 수정해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다." + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Log.d("SUA-PRM", scheduleModel.getScheduleId());
                Log.d("SUA-PRM", title.getText().toString());
                Log.d("SUA-PRM", description.getText().toString());
                Log.d("SUA-PRM", category_spinner.getSelectedItem().toString());
                Log.d("SUA-PRM", location.getText().toString());
                Log.d("SUA-PRM", start_time.getText().toString());
                Log.d("SUA-PRM", end_time.getText().toString());
                Log.d("SUA-PRM", start_date.getText().toString());
                Log.d("SUA-PRM", end_date.getText().toString());
                Log.d("SUA-PRM", String.valueOf(alarmCheck.isChecked() ? 1 : -1));
                Log.d("SUA-PRM", String.valueOf(repeatType));

                params.put("sch_id", scheduleModel.getScheduleId());
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
        queue.add(updateCategoryRequest);
    }
}