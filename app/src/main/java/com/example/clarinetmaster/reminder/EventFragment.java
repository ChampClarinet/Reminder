package com.example.clarinetmaster.reminder;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clarinetmaster.reminder.Tools.Utils;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {

    private static final String TAG = EventFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "title";
    private static final String ARG_DATE = "date";
    private static final String ARG_DESC = "description";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private long mDate;
    private String mDesc;


    public EventFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFragment newInstance(String title, long timeInMillis, String description) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TITLE, title);
        args.putLong(ARG_DATE, timeInMillis);
        args.putString(ARG_DESC, description);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mDate = getArguments().getLong(ARG_DATE);
            mDesc = getArguments().getString(ARG_DESC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleTextView = (TextView) view.findViewById(R.id.desc_event_title_text_view);
        TextView dayLeftTextView = (TextView) view.findViewById(R.id.desc_event_days_left_text_view);
        TextView descTextView = (TextView) view.findViewById(R.id.desc_event_detail_text_view);

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(mDate);
        titleTextView.setText(mTitle);
        dayLeftTextView.setText(Utils.getRemainTimeLabelText(getContext(), time));
        descTextView.setText(mDesc);

    }
}
