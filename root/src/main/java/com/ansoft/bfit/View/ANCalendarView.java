package com.ansoft.bfit.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.SquareLinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class ANCalendarView extends SquareLinearLayout {
    private static final int DAYS_COUNT = 35;
    private static final String DATE_FORMAT = "MMM yyyy";
    private String dateFormat;
    private Calendar currentDate = Calendar.getInstance();

    //event handling
    private EventHandler eventHandler = null;

    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    private TextView calDayTxt;
    private RelativeLayout outerView;
    private ImageView icCheck;

    ArrayList<Date> doneDaysArraylist;

    private int selectedDay = 0;
    private int selectedMonth = 0;


    public ANCalendarView(Context context) {
        super(context);
    }

    public ANCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public ANCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    public void setDoneDaysArraylist(ArrayList<Date> doneDaysArraylist) {
        this.doneDaysArraylist = doneDaysArraylist;
    }

    public void addDoneDay(Date date) {
        this.doneDaysArraylist.add(date);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        doneDaysArraylist = new ArrayList<>();
        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.ancal_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById(R.id.cal_header);
        btnPrev = findViewById(R.id.prevBtn);
        btnNext = findViewById(R.id.nextBtn);
        txtDate = findViewById(R.id.calDate);
        grid = findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers() {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                selectedDay = 0;
                selectedMonth = 0;
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                selectedDay = 0;
                selectedMonth = 0;
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
                return true;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (eventHandler != null) {

                    eventHandler.onDayPress((Date) adapterView.getItemAtPosition(i));
                    selectedDay = ((Date) adapterView.getItemAtPosition(i)).getDate();
                    selectedMonth = ((Date) adapterView.getItemAtPosition(i)).getMonth();
                    updateCalendar();
                }
            }

        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar() {
        updateCalendar(null);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();
            Date today = new Date();
            if (view == null) {
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            }
            calDayTxt = view.findViewById(R.id.cal_day_txt);
            outerView = view.findViewById(R.id.outerView);
            icCheck = view.findViewById(R.id.icCheck);
            calDayTxt.setTypeface(null, Typeface.NORMAL);
            calDayTxt.setTextColor(getContext().getColor(R.color.black));
            if (month != today.getMonth() || year != today.getYear()) {
                calDayTxt.setTextColor(getContext().getColor(R.color.lightgrey));
            } else if (day == today.getDate()) {
                calDayTxt.setTypeface(null, Typeface.BOLD);
                calDayTxt.setTextColor(getContext().getColor(R.color.colorAccent));
            }
            if (day == selectedDay && month == selectedMonth) {
                calDayTxt.setTypeface(null, Typeface.BOLD);
                calDayTxt.setTextColor(getContext().getColor(R.color.green));
            }
            calDayTxt.setText(String.valueOf(date.getDate()));
            for (Date eventDate : doneDaysArraylist) {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    outerView.setBackgroundResource(R.drawable.bg_circle_primary);
                    calDayTxt.setVisibility(INVISIBLE);
                    icCheck.setVisibility(VISIBLE);
                    break;
                }
            }
            return view;
        }
    }

    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler {
        void onDayLongPress(Date date);

        void onDayPress(Date date);
    }
}
