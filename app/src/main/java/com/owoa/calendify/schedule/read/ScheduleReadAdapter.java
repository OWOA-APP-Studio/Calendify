package com.owoa.calendify.schedule.read;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.owoa.calendify.R;
import com.owoa.calendify.schedule.ScheduleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScheduleReadAdapter extends BaseAdapter {
    ScheduleReadActivity activity;
    LayoutInflater layoutInflater;
    JSONArray schedules;

    public ScheduleReadAdapter(Activity activity, JSONArray schedules) {
        this.activity = (ScheduleReadActivity) activity;
        this.schedules = schedules;
        this.layoutInflater = LayoutInflater.from(activity.getApplicationContext());
    }
    @Override
    public int getCount() {
        Log.d("SDR-getCount()", schedules.length()+"");
        return schedules.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return schedules.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_schedule, null);
        TextView title = view.findViewById(R.id.schedule_name);
        TextView description = view.findViewById(R.id.schedule_item_description);

        try {
            if(schedules != null) {
                ScheduleModel model = new ScheduleModel(schedules.getJSONObject(position));
                title.setText(model.getTitle());
                description.setText(model.getDescription());
            }
            else {
                Toast.makeText(activity, "값이 없음", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
