package com.example.clarinetmaster.reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.clarinetmaster.reminder.Adapters.PagerAdapter;
import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.Tools.EventList;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class EventDescriptionActivity extends AppCompatActivity {

    private Calendar time;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int startPosition = getIntent().getIntExtra("curPosition", 0);

        PagerAdapter adapter = new PagerAdapter(this, getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setCurrentItem(startPosition);
        pager.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EventDescriptionActivity.this);
                dialog.setTitle(R.string.confirm_delete_label);
                dialog.setMessage(R.string.confirm_delete_message);
                dialog.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventList list = EventList.getInstance(getApplicationContext());
                        int position = pager.getCurrentItem();
                        list.deleteData(list.getEventList().get(position).getId());
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            editDialog(pager.getCurrentItem());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editDialog(int position) {

        EventList menu = EventList.getInstance(this);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        final Event curItem = menu.getEventList().get(position);
        time = Calendar.getInstance();

        View layout = inflater.inflate(R.layout.event_form_layout, null);
        ImageView dateImageView = (ImageView) layout.findViewById(R.id.date_image_view);
        ImageView timeImageView = (ImageView) layout.findViewById(R.id.time_image_view);
        final EditText eventLabelEditText = (EditText) layout.findViewById(R.id.event_label_edit_text);
        final EditText eventDescEditText = (EditText) layout.findViewById(R.id.detail_edit_text);

        eventLabelEditText.setText(curItem.getTitle());
        eventDescEditText.setText(curItem.getDetial());
        time = curItem.getDate();

        TimePickerDialog.OnTimeSetListener onTimeSet = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);
            }
        };

        DatePickerDialog.OnDateSetListener onDateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                time.set(Calendar.YEAR, year);
                time.set(Calendar.MONTH, month);
                time.set(Calendar.DAY_OF_MONTH, day);
            }
        };

        mDatePicker = DatePickerDialog.newInstance(
                onDateSet,
                time.get(Calendar.YEAR),
                time.get(Calendar.MONTH),
                time.get(Calendar.DAY_OF_MONTH),
                false
        );
        mTimePicker = TimePickerDialog.newInstance(onTimeSet,
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
                if (label.length() > 0) {
                    Event newEvent = new Event(
                            label,
                            eventDescEditText.getText().toString(),
                            time
                    );
                    EventList.updateData(curItem.getId(), newEvent);
                    dialog.dismiss();
                    finish();
                } else Toast.makeText(EventDescriptionActivity.this, R.string.label_blank_toast, Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setView(layout);
        dialog.show();

    }

}