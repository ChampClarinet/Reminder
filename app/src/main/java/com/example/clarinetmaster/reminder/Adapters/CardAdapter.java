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
        public ImageView warn1;
        public ImageView warn2;

        public ViewHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.label);
            time = (TextView) itemView.findViewById(R.id.time);
            desc = (TextView) itemView.findViewById(R.id.desc);
            card = (CardView) itemView.findViewById(R.id.card_view);
            warn1 = (ImageView) itemView.findViewById(R.id.warning);
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
        if(curItem.getDetial().length() > 0) holder.desc.setText(curItem.getDetial());
        else holder.desc.setVisibility(View.GONE);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDescriptionActivity.class);
                intent.putExtra("curPosition", position);
                context.startActivity(intent);
            }
        });
        CardsColour colour = CardsColour.getInstance();
        holder.card.setBackgroundResource(colour.getColour(curItem.getDate()));
        if(Utils.getRemainingTime(curItem.getDate()) > 24 * 60 * 60 * 1000 || Utils.getRemainingTime(curItem.getDate()) < 0) holder.warn1.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return eventList.getEventList().size();
    }
}
