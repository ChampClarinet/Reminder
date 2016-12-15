package com.example.clarinetmaster.reminder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clarinetmaster.reminder.EventDescriptionActivity;
import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.R;
import com.example.clarinetmaster.reminder.Tools.CardsColour;
import com.example.clarinetmaster.reminder.Tools.EventList;
import com.example.clarinetmaster.reminder.Tools.Utils;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private static final String TAG = CardAdapter.class.getSimpleName();

    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView label;
        public TextView date;
        public TextView time;
        public CardView card;
        public ImageView warn;

        public ViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.label);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            card = (CardView) itemView.findViewById(R.id.card_view);
            warn = (ImageView) itemView.findViewById(R.id.warning);
        }

    }

    public CardAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EventList eventList = EventList.getInstance(context);
        Event curItem = eventList.getEventList().get(position);
        holder.label.setText(curItem.getTitle());
        holder.date.setText(Utils.dateLabel(context, curItem.getDate()));
        holder.time.setText(Utils.timeLabel(context, curItem.getDate()));
        final int curPosition = position;
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("curPosition", curPosition);
                context.startActivity(intent);
            }
        });
        CardsColour colour = CardsColour.getInstance();
        holder.card.setBackgroundResource(colour.getColour(curItem.getDate()));
        if(Utils.getRemainingTimeLong(curItem.getDate()) > 24 * 60 * 60 * 1000 || Utils.getRemainingTimeLong(curItem.getDate()) < 0) holder.warn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        EventList eventList = EventList.getInstance(context);
        return eventList.getEventList().size();
    }
}
