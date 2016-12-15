package com.example.clarinetmaster.reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clarinetmaster.reminder.Adapters.CardAdapter;

import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.Tools.EventList;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView noItemLabel;

    private Calendar time;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEventDialog();
            }
        });
        noItemLabel = (TextView) findViewById(R.id.no_item_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();

        CardAdapter adapter = new CardAdapter(this);

        RecyclerView feedRecyclerView = (RecyclerView) findViewById(R.id.feedRecycler);
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerView.setAdapter(adapter);

        if(adapter.getItemCount() == 0) noItemLabel.setVisibility(View.VISIBLE);
        else noItemLabel.setVisibility(View.GONE);
    }

    private void addEventDialog() {

        time = Calendar.getInstance();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.event_form_layout, null);
        ImageView dateImageView = (ImageView) layout.findViewById(R.id.date_image_view);
        ImageView timeImageView = (ImageView) layout.findViewById(R.id.time_image_view);
        final EditText eventLabelEditText = (EditText) layout.findViewById(R.id.event_label_edit_text);
        final EditText eventDescEditText = (EditText) layout.findViewById(R.id.detail_edit_text);

        mDatePicker = DatePickerDialog.newInstance(
                onDateSet,
                time.get(Calendar.YEAR),
                time.get(Calendar.MONTH),
                time.get(Calendar.DAY_OF_MONTH),
                false
        );
        mTimePicker = TimePickerDialog.newInstance(
                onTimeSet,
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE),
                true,
                false
        );

        dateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        timeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        dialog.setTitle(R.string.create_dialog_title);

        dialog.setNeutralButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String label = eventLabelEditText.getText().toString();
                if(label.length() > 0){
                    Event newEvent = new Event(
                            label,
                            eventDescEditText.getText().toString(),
                            time
                    );
                    EventList.insertData(newEvent);
                    noItemLabel.setVisibility(View.GONE);
                    dialog.dismiss();
                }else Toast.makeText(MainActivity.this, R.string.label_blank_toast, Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setView(layout);
        dialog.show();

    }

    private final TimePickerDialog.OnTimeSetListener onTimeSet = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);
        }
    };

    private final DatePickerDialog.OnDateSetListener onDateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
            time.set(year, month, day);
        }
    };

}