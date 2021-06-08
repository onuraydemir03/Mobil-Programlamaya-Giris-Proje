package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    ImageButton drawersButton, itemsButton, cabinButton, eventsButton;
    ItemsDB itemsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 102);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        drawersButton = findViewById(R.id.menuDrawersButton);
        itemsButton = findViewById(R.id.menuItemsButton);
        cabinButton = findViewById(R.id.menuCabinButton);
        eventsButton = findViewById(R.id.menuEventsButton);

        itemsDB = new ItemsDB(MenuActivity.this);
        buttonActions();
    }

    public void buttonActions(){
        drawersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = itemsDB.getLastID();
                if(id == 0){
                    AlertDialog dialog =  new AlertDialog.Builder(MenuActivity.this)
                            .setMessage("There is no items that added to the app.. Please add items first..")
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
                startActivity(new Intent(MenuActivity.this, DrawersActivity.class));
            }
        });
        itemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ItemsActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
            }
        });
        cabinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = itemsDB.getLastID();
                if(id == 0){
                    AlertDialog dialog =  new AlertDialog.Builder(MenuActivity.this)
                            .setMessage("There is no items that added to the app.. Please add items first..")
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
                startActivity(new Intent(MenuActivity.this, CabinsActivity.class));
            }
        });
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, EventsActivity.class));
            }
        });
    }
}