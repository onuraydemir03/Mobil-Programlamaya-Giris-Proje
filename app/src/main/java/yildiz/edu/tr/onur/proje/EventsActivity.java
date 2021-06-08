package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    RecyclerView eventsRecyclerView;
    ImageButton addNewEventButton;
    ArrayList<Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_events);

        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        addNewEventButton = findViewById(R.id.eventsAddNewEventButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(EventsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        eventsRecyclerView.setLayoutManager(layoutManager);
        eventsRecyclerView.setHasFixedSize(true);


        EventsDB db = new EventsDB(EventsActivity.this);

        events = db.getAllEvents();
        EventsAdapter eventsAdapter = new EventsAdapter(events, EventsActivity.this);
        eventsRecyclerView.setAdapter(eventsAdapter);

        buttonActions();
    }

    public void buttonActions(){
        addNewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventsActivity.this, AddNewEventActivity.class);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });
    }
}