package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class CabinActivity extends AppCompatActivity {
    ImageButton hatButton, glassesButton, upperBodyButton, lowerBodyButton, shoeButton, saveButton;
    int hatID, glassID, upperID, lowerID, shoeID;
    File src;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cabin);
        Intent intent = getIntent();
        hatID = intent.getIntExtra("hatID", -1);
        glassID = intent.getIntExtra("glassID", -1);
        upperID = intent.getIntExtra("upperID", -1);
        lowerID = intent.getIntExtra("lowerID", -1);
        shoeID = intent.getIntExtra("shoeID", -1);

        ItemsDB itemsDB = new ItemsDB(CabinActivity.this);
        hatButton = findViewById(R.id.cabinHatButton);
        glassesButton = findViewById(R.id.cabinGlassesButton);
        upperBodyButton = findViewById(R.id.cabinUpperBodyButton);
        lowerBodyButton = findViewById(R.id.cabinLowerBodyButton);
        shoeButton = findViewById(R.id.cabinFootButton);
        saveButton = findViewById(R.id.cabinSaveButton);

        if(hatID != -1) {
            Item hat = itemsDB.getAnItemWID(hatID);
            src = new File(getFilesDir() + File.separator + "Items" + File.separator + hat.getImgName());
            if (src.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                hatButton.setImageBitmap(myBitmap);
            }
        }
        if(glassID != -1) {
            Item glass = itemsDB.getAnItemWID(glassID);
            src = new File(getFilesDir() + File.separator + "Items" + File.separator + glass.getImgName());
            if (src.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                glassesButton.setImageBitmap(myBitmap);
            }
        }
        if(upperID != -1) {
            Item upper = itemsDB.getAnItemWID(upperID);
            src = new File(getFilesDir() + File.separator + "Items" + File.separator + upper.getImgName());
            if (src.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                upperBodyButton.setImageBitmap(myBitmap);
            }
        }
        if(lowerID != -1) {
            Item lower = itemsDB.getAnItemWID(lowerID);
            src = new File(getFilesDir() + File.separator + "Items" + File.separator + lower.getImgName());
            if (src.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                lowerBodyButton.setImageBitmap(myBitmap);
            }
        }
        if(shoeID != -1) {
            Item shoe = itemsDB.getAnItemWID(shoeID);
            src = new File(getFilesDir() + File.separator + "Items" + File.separator + shoe.getImgName());
            if (src.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                shoeButton.setImageBitmap(myBitmap);
            }
        }
        buttonActions();
    }

    public void buttonActions(){
        hatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CabinActivity.this, DrawersActivity.class);
                intent.putExtra("hatID", hatID);
                intent.putExtra("glassID", glassID);
                intent.putExtra("upperID", upperID);
                intent.putExtra("lowerID", lowerID);
                intent.putExtra("shoeID", shoeID);
                intent.putExtra("requestCode", 1001);
                startActivity(intent);
            }
        });
        glassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CabinActivity.this, DrawersActivity.class);
                intent.putExtra("hatID", hatID);
                intent.putExtra("glassID", glassID);
                intent.putExtra("upperID", upperID);
                intent.putExtra("lowerID", lowerID);
                intent.putExtra("shoeID", shoeID);
                intent.putExtra("requestCode", 1002);
                startActivity(intent);
            }
        });
        upperBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CabinActivity.this, DrawersActivity.class);
                intent.putExtra("hatID", hatID);
                intent.putExtra("glassID", glassID);
                intent.putExtra("upperID", upperID);
                intent.putExtra("lowerID", lowerID);
                intent.putExtra("shoeID", shoeID);
                intent.putExtra("requestCode", 1003);
                startActivity(intent);
            }
        });
        lowerBodyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CabinActivity.this, DrawersActivity.class);
                intent.putExtra("hatID", hatID);
                intent.putExtra("glassID", glassID);
                intent.putExtra("upperID", upperID);
                intent.putExtra("lowerID", lowerID);
                intent.putExtra("shoeID", shoeID);
                intent.putExtra("requestCode", 1004);
                startActivity(intent);
            }
        });
        shoeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CabinActivity.this, DrawersActivity.class);
                intent.putExtra("hatID", hatID);
                intent.putExtra("glassID", glassID);
                intent.putExtra("upperID", upperID);
                intent.putExtra("lowerID", lowerID);
                intent.putExtra("shoeID", shoeID);
                intent.putExtra("requestCode", 1005);
                startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hatID == -1 || glassID == -1 || upperID == -1 || lowerID == -1 || shoeID == -1){
                    AlertDialog dialog =  new AlertDialog.Builder(CabinActivity.this)
                            .setMessage("Please select all the images for your complete combine..")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    //
                                }//end onClick()
                            }).create();
                    dialog.show();
                }else{
                    Combine combine = new Combine(-1,hatID, glassID, upperID, lowerID, shoeID);
                    CombinesDB combinesDB = new CombinesDB(CabinActivity.this);
                    combinesDB.addNewCombine(combine);
                    AlertDialog dialog =  new AlertDialog.Builder(CabinActivity.this)
                            .setMessage("Congrats ! You added this combine successfully..")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1)
                                {
                                    startActivity(new Intent(CabinActivity.this, MenuActivity.class));
                                }//end onClick()
                            }).create();
                    dialog.show();
                }

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(CabinActivity.this, MenuActivity.class));
    }
}