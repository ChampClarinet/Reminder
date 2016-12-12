package com.example.clarinetmaster.reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clarinetmaster.reminder.Adapters.PagerAdapter;
import com.example.clarinetmaster.reminder.Tools.EventList;
import com.example.clarinetmaster.reminder.Tools.Utils;

public class EventDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int startPosition = getIntent().getIntExtra("curPosition", 0);

        final PagerAdapter adapter = new PagerAdapter(this, getSupportFragmentManager());
        final ViewPager pager = (ViewPager)findViewById(R.id.view_pager);
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
                        list.deleteData(list.getEventList().get(startPosition).getId());
                        finish();
                        dialog.dismiss();
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

}
