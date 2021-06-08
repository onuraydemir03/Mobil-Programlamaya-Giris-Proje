package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNewItemActivity extends AppCompatActivity {
    Spinner kindSpinner, colorSpinner, patternSpinner;
    EditText buyingDateText, priceText;
    ImageButton getImageButton, addNewItemButton;
    int id;
    File src, dst;
    private static final int RESULT_LOAD_IMAGE = 1;
    private DatePickerDialog mDatePickerDialog;
    boolean gotImage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_item);
        kindSpinner = findViewById(R.id.addNewItemTypeSpinner);
        String[] kinds = {"Trousers", "T-Shirt", "Hat", "Shoe", "Glasses"};
        ArrayAdapter<String> adapterKind = new ArrayAdapter<String>(AddNewItemActivity.this,
                android.R.layout.simple_spinner_item,kinds);
        adapterKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindSpinner.setAdapter(adapterKind);

        colorSpinner = findViewById(R.id.addNewItemColorSpinner);
        String[] colors = {"Red", "White", "Blue", "Yellow", "Orange", "Pink", "Purple", "Brown","Grey", "Black","Green"};
        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(AddNewItemActivity.this,
                android.R.layout.simple_spinner_item,colors);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapterColor);

        patternSpinner = findViewById(R.id.addNewItemPatternSpinner);
        String[] patterns = {"Straight", "Striped", "Plaid"};
        ArrayAdapter<String> adapterPattern = new ArrayAdapter<String>(AddNewItemActivity.this,
                android.R.layout.simple_spinner_item,patterns);
        adapterPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patternSpinner.setAdapter(adapterPattern);

        buyingDateText = findViewById(R.id.addNewItemDate);
        setDateTimeField();

        priceText = findViewById(R.id.addNewItemPrice);
        getImageButton = findViewById(R.id.addNewItemImage);
        addNewItemButton = findViewById(R.id.addNewItemButton);
        ItemsDB db = new ItemsDB(AddNewItemActivity.this);
        id = db.getLastID() + 1;

        buttonActions();
    }

    public void buttonActions(){
        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        addNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priceText.getText().toString().trim().equals("") || buyingDateText.getText().toString().trim().equals("")){
                    Toast.makeText(AddNewItemActivity.this, "Please pick a date and enter a price !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!gotImage){
                    Toast.makeText(AddNewItemActivity.this, "Please pick image of your item !", Toast.LENGTH_SHORT).show();
                    return;
                }
                String kind = kindSpinner.getSelectedItem().toString();
                String color = colorSpinner.getSelectedItem().toString();
                String pattern = patternSpinner.getSelectedItem().toString();
                String date = buyingDateText.getText().toString();
                String price = priceText.getText().toString();
                Item item = new Item(id,kind,color,pattern,String.valueOf(id)+".jpg",date, Integer.valueOf(price));
                ItemsDB db = new ItemsDB(AddNewItemActivity.this);
                db.addNewItem(item);


                AlertDialog dialog =  new AlertDialog.Builder(AddNewItemActivity.this)
                        .setMessage("Congrats ! You added item successfully..")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                try
                                {
                                    copyFile(src, dst);
                                }//end try
                                catch(Exception e)
                                {
                                    Toast.makeText(getBaseContext(),  "", Toast.LENGTH_LONG).show();
                                }//end catch
                                startActivity(new Intent(AddNewItemActivity.this, MenuActivity.class));
                            }//end onClick()
                        }).create();
            dialog.show();
            }
        });

        buyingDateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String filePath;
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    gotImage = true;
                    Uri fileUri = data.getData();
                    filePath = fileUri.toString();
                    String[] parts = fileUri.getLastPathSegment().split(":");
                    src = new File(Environment.getExternalStorageDirectory()+File.separator+parts[1]);
                    if (src.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                        getImageButton.setImageBitmap(myBitmap);
                    }
                    dst = new File(getFilesDir() + File.separator + "Items");
                    if(!dst.exists())
                        dst.mkdir();
                }
        }
    }

    private void copyFile(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        String name = String.valueOf(id) + ".jpg";
        dst = new File(getFilesDir() + File.separator + "Items"+ File.separator + name);
        String filePath = dst.toString();
        if (!dst.exists()) {
            dst.createNewFile();
        }
        if (!dst.canWrite()) {
            return;
        }
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
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
                buyingDateText.setText(fdate);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
}