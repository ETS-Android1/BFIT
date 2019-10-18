package com.ansoft.bfit.Fragments.Home;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.ansoft.bfit.DataModel.ReportData;
import com.ansoft.bfit.R;
import com.ansoft.bfit.View.ANCalendarView;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {


    public ReportFragment() {
        // Required empty public constructor
    }

    ANCalendarView anCalendarView;

    TextView tvCurrentDate, tvCompleted, tvCalorie, tvTime;
    ArrayList<Date> completedDateArraylist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        anCalendarView = view.findViewById(R.id.calendar_view);

        tvCurrentDate = view.findViewById(R.id.currentDate);
        tvCompleted = view.findViewById(R.id.tvCompleted);
        tvCalorie = view.findViewById(R.id.tvCalorie);
        tvTime = view.findViewById(R.id.tvTime);


        anCalendarView.setEventHandler(new ANCalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {

            }

            @Override
            public void onDayPress(Date date) {

                loadCurrentDateDate(date);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        Calendar cal1 = Calendar.getInstance();
        int currentDaycmp = cal1.get(Calendar.DAY_OF_MONTH);
        int currentMonthcmp = cal1.get(Calendar.MONTH);
        int currentYearcmp = cal1.get(Calendar.YEAR);
        loadCurrentDateDate(getDate(currentYearcmp, currentMonthcmp, currentDaycmp));


        completedDateArraylist = new ArrayList<>();
        ArrayList<ReportData> reportDataArrayList = (ArrayList<ReportData>) Select.from(ReportData.class).where(Condition.prop("completed").eq(1)).list();
        for (ReportData reportData : reportDataArrayList) {
            Log.e("ss", reportData.getDay()+"/"+reportData.getMonth()+"/"+reportData.getYear());
            completedDateArraylist.add(getDate(reportData.getYear(), reportData.getMonth(), reportData.getDay()));
        }
        anCalendarView.setDoneDaysArraylist(completedDateArraylist);
        anCalendarView.updateCalendar();
    }

    private void loadCurrentDateDate(Date date) {
        Log.e("Month", date.getYear()+"");
        tvCurrentDate.setText((date.getYear()+1900) + "/" +String.format("%02d", (date.getMonth()+1))  + "/" + String.format("%02d", (date.getDate())));
        ReportData reportData = Select.from(ReportData.class)
                .where(Condition.prop("year").eq(date.getYear()+1900),
                        Condition.prop("month").eq(date.getMonth()),
                        Condition.prop("day").eq(date.getDate())).first();

        if (reportData != null) {
            int Minutes = reportData.getTotalSeconds() / 60;
            tvCompleted.setText(reportData.getTotalCompleted() + "");
            tvCalorie.setText(reportData.getTotalCalorieBurned() + "");
            tvTime.setText(Minutes + "");
        } else {
            tvCompleted.setText("0");
            tvCalorie.setText("0");
            tvTime.setText("0");
        }
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = formatter.parse(dateString);//catch exception
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

}
