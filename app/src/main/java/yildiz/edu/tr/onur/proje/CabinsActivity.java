package yildiz.edu.tr.onur.proje;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CabinsActivity extends AppCompatActivity {

    RecyclerView cabinsRecyclerView;
    ImageButton addNewTryButton;
    ArrayList<Combine> combines;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cabins);

        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);

        cabinsRecyclerView = findViewById(R.id.cabinsRecyclerView);
        addNewTryButton = findViewById(R.id.cabinsAddNewTryButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CabinsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        cabinsRecyclerView.setLayoutManager(layoutManager);
        cabinsRecyclerView.setHasFixedSize(true);

        CombinesDB db = new CombinesDB(CabinsActivity.this);
        combines = db.getAllCombines();
        CombinesAdapter combinesAdapter = new CombinesAdapter(combines, flag, CabinsActivity.this);
        cabinsRecyclerView.setAdapter(combinesAdapter);

        if(flag == 0){
            addNewTryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CabinsActivity.this, CabinActivity.class));
                }
            });
        }else{
            addNewTryButton.setVisibility(View.GONE);
        }


    }
}