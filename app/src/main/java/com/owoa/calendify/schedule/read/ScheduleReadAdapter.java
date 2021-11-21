package com.owoa.calendify.schedule.read;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.owoa.calendify.R;
import com.owoa.calendify.schedule.create.ScheduleModel;
import com.owoa.calendify.schedule.delete.ScheduleDeletePresenter;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleReadAdapter extends BaseAdapter {
    ScheduleReadActivity activity;
    LayoutInflater layoutInflater;
    ArrayList<ScheduleModel> scheduleModels = new ArrayList<>();
    Date nowDateTime;

    public ScheduleReadAdapter(Activity activity, JSONArray schedules) {
        this.activity = (ScheduleReadActivity) activity;
        this.layoutInflater = LayoutInflater.from(activity.getApplicationContext());

        for (int i = 0; i < schedules.length(); i++) {
            try {
                scheduleModels.add(new ScheduleModel(schedules.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        nowDateTime = new Date();
    }

    @Override
    public int getCount() {
        Log.d("SDR-getCount()", scheduleModels.size() + "");
        return scheduleModels.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleModels.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_schedule, null);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] select = {"일정 수정", "일정 삭제"};

                AlertDialog.Builder oDialog = new AlertDialog.Builder(activity,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                oDialog.setTitle("일정 관리");
                oDialog.setItems(select, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ScheduleModel model = scheduleModels.get(position);
                                Toast.makeText(activity, select[which] + " : " + model.getScheduleId(), Toast.LENGTH_LONG).show();

                                switch (which) {
                                    case 0:
                                        Toast.makeText(activity, "일정 수정 기능입니다.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(activity, "일정을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                                        ScheduleDeletePresenter presenter = new ScheduleDeletePresenter(activity, activity.getUid(), model.getScheduleId());
                                        presenter.deleteSchedule();
                                }

                            }
                        });
                oDialog.show();
                return false;
            }
        });


        TextView title = view.findViewById(R.id.schedule_name);
        TextView description = view.findViewById(R.id.schedule_item_description);
        TextView startDay = view.findViewById(R.id.schedule_item_today);
        TextView location = view.findViewById(R.id.schedule_item_location);

        TextView startTime = view.findViewById(R.id.schedule_item_start_time);
        TextView endTime = view.findViewById(R.id.schedule_item_end_time);

        ProgressBar progressBar = view.findViewById(R.id.schedule_progress_bar);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

        try {
            if (scheduleModels != null) {
                ScheduleModel model = scheduleModels.get(position);
                Date startDayDate = dateFormat.parse(model.getStartDay());
                Date startTimeDate = timeFormat.parse(model.getStartTime());
                Date endTimeDate = timeFormat.parse(model.getEndTime());

                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
                SimpleDateFormat stmFormat = new SimpleDateFormat("a hh:mm");

                if (isNowInDay(model.getStartDay(), model.getEndDay())) {
                    if (isNowInTime(model.getStartTime(), model.getEndTime())) {
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(setProgress(model.getStartTime(), model.getEndTime()));
                        view.setBackground(view.getContext().getDrawable(R.color.signature));
                    }
                }

                if (position > 0) {
                    ScheduleModel forwardSchedule = scheduleModels.get(position - 1);
                    if (model.getStartDay().split("-")[2].equals(forwardSchedule.getStartDay().split("-")[2])) {
                        startDay.setVisibility(View.INVISIBLE);
                        startDay.getLayoutParams().width = 80;
                        startDay.getLayoutParams().height = 80;
                    } else {
                        LinearLayout layout1 = view.findViewById(R.id.schedule_item_split1);
                        layout1.setBackground(view.getContext().getDrawable(R.color.black));
                        LinearLayout layout2 = view.findViewById(R.id.schedule_item_split2);
                        layout2.setBackground(view.getContext().getDrawable(R.color.black));
                    }
                }

                startDay.setText(dayFormat.format(startDayDate));
                startTime.setText(stmFormat.format(startTimeDate));
                endTime.setText(stmFormat.format(endTimeDate));
                if (!model.getLocation().equals("null"))
                    location.setText(model.getLocation());

                title.setText(model.getTitle());
                description.setText(model.getDescription());
            } else {
                Toast.makeText(activity, "값이 없음", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int setProgress(String startTime, String endTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String nowTime = nowDateTime.getHours() + ":" + nowDateTime.getMinutes() + ":" + nowDateTime.getSeconds();

        int progress = 0;
        try {
            Date nowTimeDate = timeFormat.parse(nowTime);
            Date startTimeDate = timeFormat.parse(startTime);
            Date endTimeDate = timeFormat.parse(endTime);

            long timeLength = (startTimeDate.getTime() - endTimeDate.getTime()) / 1000L;
            long currentTime = (startTimeDate.getTime() - nowTimeDate.getTime()) / 1000L;

            progress = (int) ((double) currentTime / timeLength * 100);
        } catch (ParseException e) {
            Log.d("setProgress", e.getMessage());
            e.printStackTrace();
        }

        return progress;
    }

    private boolean isNowInTime(String startTime, String endTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String nowTime = nowDateTime.getHours() + ":" + nowDateTime.getMinutes() + ":" + nowDateTime.getSeconds();
        try {
            Date nowTimeDate = timeFormat.parse(nowTime);
            Date startTimeDate = timeFormat.parse(startTime);
            Date endTimeDate = timeFormat.parse(endTime);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTimeDate);
            startCalendar.add(Calendar.DATE, 1);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTimeDate);
            endCalendar.add(Calendar.DATE, 1);

            Calendar nowCalendar = Calendar.getInstance();
            nowCalendar.setTime(nowTimeDate);
            nowCalendar.add(Calendar.DATE, 1);

            Date x = nowCalendar.getTime();

            return (x.after(startCalendar.getTime()) && x.before(endCalendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isNowInDay(String startDayString, String endDayString) {
        try {
            Log.d("isNowInDay", (startDayString.split("-")[2]) + " | " + nowDateTime.getDate());
            int startDate = Integer.parseInt(startDayString.split("-")[2]);
            int endDate = Integer.parseInt(endDayString.split("-")[2]);

            return (startDate <= nowDateTime.getDate() && endDate >= nowDateTime.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
