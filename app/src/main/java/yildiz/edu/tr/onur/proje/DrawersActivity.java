package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class DrawersActivity extends AppCompatActivity {
    RecyclerView drawersRecyclerView;
    ArrayList<Drawer> drawers;
    ImageButton addNewDrawerButton;
    int requestcode;
    int hatID, glassID, upperID, lowerID, shoeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_drawers);
        addNewDrawerButton = findViewById(R.id.drawersCreateNewDrawerButton);
        drawersRecyclerView = findViewById(R.id.drawersRecyclerView);
        Intent intent = getIntent();
        requestcode = intent.getIntExtra("requestCode", 0);
        hatID = intent.getIntExtra("hatID", -1);
        glassID = intent.getIntExtra("glassID", -1);
        upperID = intent.getIntExtra("upperID", -1);
        lowerID = intent.getIntExtra("lowerID", -1);
        shoeID = intent.getIntExtra("shoeID", -1);
        Integer [] ids = new Integer[]{hatID, glassID, upperID, lowerID, shoeID};

        LinearLayoutManager layoutManager = new LinearLayoutManager(DrawersActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        drawersRecyclerView.setLayoutManager(layoutManager);
        drawersRecyclerView.setHasFixedSize(true);

        DrawersDB db = new DrawersDB(DrawersActivity.this);

        drawers = db.getAllDrawers();
        DrawersAdapter drawersAdapter = new DrawersAdapter(drawers, requestcode, ids, DrawersActivity.this);
        drawersRecyclerView.setAdapter(drawersAdapter);

        addNewDrawerButton.setOnClickListener(new View.OnClickListener() {
            private String drawerName = "";
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DrawersActivity.this);
                builder.setTitle("Create New Drawer");
                final EditText drawerText = new EditText(DrawersActivity.this);
                drawerText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(drawerText);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawerName = drawerText.getText().toString();
                        Drawer drawer = new Drawer(-1,drawerName);
                        drawers.add(drawer);
                        DrawersDB db = new DrawersDB(DrawersActivity.this);
                        db.addNewDrawer(drawer);
                        drawers = db.getAllDrawers();
                        DrawersAdapter drawersAdapter = new DrawersAdapter(drawers, requestcode, ids, DrawersActivity.this);
                        drawersRecyclerView.setAdapter(drawersAdapter);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(DrawersActivity.this, MenuActivity.class));
    }

}