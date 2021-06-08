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

public class ItemsActivity extends AppCompatActivity {
    ImageButton addNewItemButton;
    RecyclerView itemsRecyclerView;
    ArrayList<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_items);
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);

        addNewItemButton = findViewById(R.id.itemsAddNewItemButton);
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ItemsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setHasFixedSize(true);

        ItemsDB db = new ItemsDB(ItemsActivity.this);
        items = db.getAllItems();

        ItemsAdapter itemAdapter = new ItemsAdapter(items, flag, ItemsActivity.this);
        itemsRecyclerView.setAdapter(itemAdapter);
        if(flag == 0){
            addNewItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ItemsActivity.this, AddNewItemActivity.class));
                }
            });
        }else{
            addNewItemButton.setVisibility(View.GONE);
        }

    }

}