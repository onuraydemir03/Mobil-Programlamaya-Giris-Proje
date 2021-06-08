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

public class EditItemActivity extends AppCompatActivity {
    Spinner kindSpinner, colorSpinner, patternSpinner;
    EditText buyingDateText, priceText;
    ImageButton getImageButton, submitChangesButton;
    int id;
    File src, dst;
    private static final int RESULT_LOAD_IMAGE = 1;
    private DatePickerDialog mDatePickerDialog;
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_item);
        kindSpinner = findViewById(R.id.editItemTypeSpinner);
        String[] kinds = {"Trousers", "Shirt", "T-Shirt", "Hat", "Shoe", "Jacket", "Track Suit", "Glasses","Sweat"};
        ArrayAdapter<String> adapterKind = new ArrayAdapter<String>(EditItemActivity.this,
                android.R.layout.simple_spinner_item,kinds);
        adapterKind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindSpinner.setAdapter(adapterKind);

        colorSpinner = findViewById(R.id.editItemColorSpinner);
        String[] colors = {"Red", "White", "Blue", "Yellow", "Orange", "Pink", "Purple", "Brown","Grey", "Black","Green"};
        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(EditItemActivity.this,
                android.R.layout.simple_spinner_item,colors);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapterColor);

        patternSpinner = findViewById(R.id.editItemPatternSpinner);
        String[] patterns = {"Straight", "Striped", "Plaid"};
        ArrayAdapter<String> adapterPattern = new ArrayAdapter<String>(EditItemActivity.this,
                android.R.layout.simple_spinner_item,patterns);
        adapterPattern.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patternSpinner.setAdapter(adapterPattern);

        buyingDateText = findViewById(R.id.editItemDate);
        setDateTimeField();

        priceText = findViewById(R.id.editItemPrice);
        getImageButton = findViewById(R.id.editItemImage);
        submitChangesButton = findViewById(R.id.editItemButton);

        ItemsDB db = new ItemsDB(EditItemActivity.this);
        id =  getIntent().getIntExtra("ID", 1);

        item = db.getAnItemWID(id);
        System.out.println(item.toString());
        kindSpinner.setSelection(adapterKind.getPosition(item.getKind()));
        colorSpinner.setSelection(adapterColor.getPosition(item.getColor()));
        patternSpinner.setSelection(adapterPattern.getPosition(item.getPattern()));
        buyingDateText.setText(item.getBuyingDate());
        priceText.setText(String.valueOf(item.getPrice()));
        File imgFile = new File(getFilesDir() + File.separator + "Items"+ File.separator + item.getImgName());
        Bitmap imageBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        getImageButton.setImageBitmap(imageBitmap);
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
        submitChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(priceText.getText().toString().trim().equals("") || buyingDateText.getText().toString().trim().equals("")){
                    Toast.makeText(EditItemActivity.this, "Please pick a date and enter a price !", Toast.LENGTH_SHORT).show();
                    return;
                }
                String kind = kindSpinner.getSelectedItem().toString();
                String color = colorSpinner.getSelectedItem().toString();
                String pattern = patternSpinner.getSelectedItem().toString();
                String date = buyingDateText.getText().toString();
                String price = priceText.getText().toString();

                Item item_1 = new Item(id,kind,color,pattern,item.getImgName(),date, Integer.valueOf(price));
                ItemsDB db = new ItemsDB(EditItemActivity.this);
                db.changeAnItem(id, item_1);

                AlertDialog dialog =  new AlertDialog.Builder(EditItemActivity.this)
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
                                startActivity(new Intent(EditItemActivity.this, ItemsActivity.class));
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
                    Uri fileUri = data.getData();
                    filePath = fileUri.toString();
                    String[] parts = fileUri.getLastPathSegment().split(":");
                    src = new File(Environment.getExternalStorageDirectory() + File.separator + parts[1]);
                    if (src.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(src.getAbsolutePath());
                        getImageButton.setImageBitmap(myBitmap);
                    }
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