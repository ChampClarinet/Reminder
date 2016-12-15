package com.example.clarinetmaster.reminder.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.clarinetmaster.reminder.EventFragment;
import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.Tools.EventList;

public class PagerAdapter extends FragmentPagerAdapter{

    private static final String TAG = PagerAdapter.class.getSimpleName();
    private Context context;

    public PagerAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        EventList eventList = EventList.getInstance(context);
        Event curItem = eventList.getEventList().get(position);
        return EventFragment.newInstance(curItem.getTitle(), curItem.getDate().getTimeInMillis(), curItem.getDetail());
    }

    @Override
    public int getCount() {
        EventList list = EventList.getInstance(context);
        return list.getEventList().size();
    }
}
