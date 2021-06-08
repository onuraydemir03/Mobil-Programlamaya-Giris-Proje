package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNewEventActivity extends AppCompatActivity {
    EditText nameOfEventText, typeOfEventText, dateOfEventText;
    RecyclerView combinesWearThatEventRecyclerView;
    ImageButton addNewCombineButton, saveEventButton, pickALocationButton;
    double latitude, longtitude;
    private DatePickerDialog mDatePickerDialog;
    int eventid, combineid, flag;
    ArrayList<Combine> allCombinesOfEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_event);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 41.02412565669365 );
        longtitude = intent.getDoubleExtra("longitude", 28.8941428810358);

        eventid = intent.getIntExtra("eventid", -1);
        combineid = intent.getIntExtra("combineid", -1);
        flag = intent.getIntExtra("flag", 0);


        nameOfEventText = findViewById(R.id.nameOfEventText);
        typeOfEventText = findViewById(R.id.typeOfEventText);
        dateOfEventText = findViewById(R.id.dateOfEventText);
        setDateTimeField();
        combinesWearThatEventRecyclerView = findViewById(R.id.combinesOfEventRecyclerView);
        addNewCombineButton = findViewById(R.id.eventsAddNewCombine);
        saveEventButton = findViewById(R.id.eventsSaveEvent);
        pickALocationButton = findViewById(R.id.eventPickALocationButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(AddNewEventActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        combinesWearThatEventRecyclerView.setLayoutManager(layoutManager);
        combinesWearThatEventRecyclerView.setHasFixedSize(true);

        RelationWEventCombineDB relationWEventCombineDB = new RelationWEventCombineDB(AddNewEventActivity.this);
        EventsDB eventsDB = new EventsDB(AddNewEventActivity.this);
        CombinesDB combinesDB = new CombinesDB(AddNewEventActivity.this);
        Event event;

        // Edit event..
        if(flag != 0){
            event = eventsDB.getAnEventWID(eventid);
            nameOfEventText.setText(event.getName().toString());
            typeOfEventText.setText(event.getType().toString());
            dateOfEventText.setText(event.getDate().toString());
            latitude = event.getL1();
            longtitude = event.getL2();

            allCombinesOfEvent = relationWEventCombineDB.getAllCombinesOfEvent(event);
            EventCombineAdapter eventCombineAdapter = new EventCombineAdapter(allCombinesOfEvent, eventid, AddNewEventActivity.this);
            combinesWearThatEventRecyclerView.setAdapter(eventCombineAdapter);
            if(combineid != -1){
                Combine combine = combinesDB.getACombineWID(combineid);
                event = eventsDB.getAnEventWID(eventid);
                boolean exist = false;
                for(Combine combineIter : allCombinesOfEvent){
                    if(combineIter.toString().equals(combine.toString())){
                        AlertDialog dialog =  new AlertDialog.Builder(AddNewEventActivity.this)
                                .setMessage("Error, you can not add same combine twice..")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1)
                                    {
                                        //
                                    }//end onClick()
                                }).create();
                        dialog.show();
                        exist = true;
                    }
                }
                if (!exist){
                    relationWEventCombineDB.addNewCombineToEvent(event,combine);
                }
                allCombinesOfEvent = relationWEventCombineDB.getAllCombinesOfEvent(event);
                eventCombineAdapter = new EventCombineAdapter(allCombinesOfEvent, eventid, AddNewEventActivity.this);
                combinesWearThatEventRecyclerView.setAdapter(eventCombineAdapter);
            }
        }else{
            addNewCombineButton.setVisibility(View.GONE);
            combinesWearThatEventRecyclerView.setVisibility(View.GONE);
        }

        buttonActions();
    }

    public void buttonActions() {
        pickALocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddNewEventActivity.this, MapActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longtitude);
                startActivityForResult(intent, 1923);
            }
        });
        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameOfEventText.getText().toString().equals("") || typeOfEventText.getText().toString().equals("") || dateOfEventText.getText().toString().equals("")){
                    AlertDialog dialog =  new AlertDialog.Builder(AddNewEventActivity.this)
                            .setMessage("There has been an error, please check all the empty fields..")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    //
                                }//end onClick()
                            }).create();
                    dialog.show();
                    return;
                }
                if(latitude == 41.02412565669365 || longtitude == 28.8941428810358){
                    AlertDialog dialog =  new AlertDialog.Builder(AddNewEventActivity.this)
                            .setMessage("There has been an error, please check the location of this event..")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    //
                                }//end onClick()
                            }).create();
                    dialog.show();
                    return;
                }
                Event event = new Event(eventid,nameOfEventText.getText().toString(),
                        typeOfEventText.getText().toString(),
                        dateOfEventText.getText().toString(),
                        latitude,
                        longtitude);
                EventsDB eventsDB = new EventsDB(AddNewEventActivity.this);
                if(flag == 0){
                    eventsDB.addNewEvent(event);
                    AlertDialog dialog =  new AlertDialog.Builder(AddNewEventActivity.this)
                            .setMessage("Congrats ! You added item successfully..")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    startActivity(new Intent(AddNewEventActivity.this, MenuActivity.class));
                                }//end onClick()
                            }).create();
                    dialog.show();
                }else{
                    eventsDB.changeAnEvent(event);
                    AlertDialog dialog =  new AlertDialog.Builder(AddNewEventActivity.this)
                            .setMessage("Congrats ! You changed event information successfully..")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    startActivity(new Intent(AddNewEventActivity.this, MenuActivity.class));
                                }//end onClick()
                            }).create();
                    dialog.show();
                }

            }
        });
        dateOfEventText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });

        addNewCombineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewEventActivity.this, CabinsActivity.class);
                intent.putExtra("flag",eventid);
                startActivity(intent);
            }
        });

    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);
                dateOfEventText.setText(fdate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1923) {
            if(resultCode == Activity.RESULT_OK){
                latitude = data.getDoubleExtra("latitude", 41.02412565669365 );
                longtitude = data.getDoubleExtra("longitude", 28.8941428810358);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }



}