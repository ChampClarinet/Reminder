package com.example.clarinetmaster.reminder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clarinetmaster.reminder.EventDescriptionActivity;
import com.example.clarinetmaster.reminder.MainActivity;
import com.example.clarinetmaster.reminder.Models.Event;
import com.example.clarinetmaster.reminder.R;
import com.example.clarinetmaster.reminder.Tools.CardsColour;
import com.example.clarinetmaster.reminder.Tools.EventList;
import com.example.clarinetmaster.reminder.Tools.Utils;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private static final String TAG = CardAdapter.class.getSimpleName();

    private EventList eventList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView label;
        public TextView time;
        public TextView desc;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.label);
            time = (TextView) itemView.findViewById(R.id.time);
            desc = (TextView) itemView.findViewById(R.id.desc);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }

    }

    public CardAdapter(Context context){
        this.context = context;
        this.eventList = EventList.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Event curItem = eventList.getEventList().get(position);
        holder.label.setText(curItem.getTitle());
        holder.time.setText(Utils.dateLabel(curItem.getDate()));
        holder.desc.setText(curItem.getDetial());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("curEvent", eventList.getEventList().get(position));
                context.startActivity(intent);
            }
        });
        CardsColour colour = CardsColour.getInstance();
        holder.card.setCardBackgroundColor(colour.getColour(curItem.getDate()));
    }
    @Override
    public int getItemCount() {
        return eventList.getEventList().size();
    }
}
