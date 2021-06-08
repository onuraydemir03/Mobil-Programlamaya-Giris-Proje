package yildiz.edu.tr.onur.proje;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    ArrayList<Event> events = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public EventsAdapter(ArrayList<Event> events, Context context){
        this.events = events;
        this.context = context;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.events_row, parent, false);
        return new EventsAdapter.ViewHolder(rowView);
    }
    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position){

        holder.eventNameText.setText(events.get(position).getName());
        holder.eventTypeText.setText(events.get(position).getType());
        holder.eventDateText.setText(events.get(position).getDate());
        int id = events.get(position).getId();
        holder.editEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddNewEventActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("eventid", id);
                context.startActivity(intent);
            }
        });

        holder.deleteEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete Event")
                        .setMessage("Are you sure you want to delete this event?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EventsDB eventsDB = new EventsDB(context);
                                events.remove(position);
                                notifyDataSetChanged();
                                eventsDB.deleteAnEvent(id);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView eventNameText, eventTypeText, eventDateText;
        ImageButton editEventButton, deleteEventButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.eventNameText = itemView.findViewById(R.id.eventRowNameText);
            this.eventTypeText = itemView.findViewById(R.id.eventRowTypeText);
            this.eventDateText = itemView.findViewById(R.id.eventRowDateText);
            this.editEventButton = itemView.findViewById(R.id.eventRowEditButton);
            this.deleteEventButton = itemView.findViewById(R.id.eventRowDeleteButton);
        }
    }
}
