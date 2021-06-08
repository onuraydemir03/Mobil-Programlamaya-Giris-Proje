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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DrawerActivity extends AppCompatActivity {
    ImageButton addNewItemButton;
    RecyclerView drawerRecyclerView;
    ArrayList<Item> allItemsOfDrawer;
    int hatID, glassID, upperID, lowerID, shoeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_drawer);
        Intent intent = getIntent();
        int id = intent.getIntExtra("drawerid", 1);
        int itemID = intent.getIntExtra("itemID", -1);

        hatID = intent.getIntExtra("hatID", -1);
        glassID = intent.getIntExtra("glassID", -1);
        upperID = intent.getIntExtra("upperID", -1);
        lowerID = intent.getIntExtra("lowerID", -1);
        shoeID = intent.getIntExtra("shoeID", -1);
        Integer [] ids = new Integer[]{hatID, glassID, upperID, lowerID, shoeID};

        int requestCode = intent.getIntExtra("requestCode", 0);

        addNewItemButton = findViewById(R.id.drawerAddNewItemButton);
        drawerRecyclerView = findViewById(R.id.drawerRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(DrawerActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        drawerRecyclerView.setLayoutManager(layoutManager);
        drawerRecyclerView.setHasFixedSize(true);

        RelationDB relationDB = new RelationDB(DrawerActivity.this);
        DrawersDB drawersDB = new DrawersDB(DrawerActivity.this);
        ItemsDB itemsDB = new ItemsDB(DrawerActivity.this);
        Drawer drawer = drawersDB.getADrawerWID(id);
        allItemsOfDrawer = relationDB.getAllItemsOfDrawer(drawer);

        if(itemID != -1){
            Item item = itemsDB.getAnItemWID(itemID);
            boolean exist = false;
            for(Item itemIter : allItemsOfDrawer){
                if(itemIter.toString().equals(item.toString())){
                    AlertDialog dialog =  new AlertDialog.Builder(DrawerActivity.this)
                            .setMessage("Error, you can not add same item twice..")
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
                relationDB.addNewItemToDrawer(drawer,item);
            }
        }

        allItemsOfDrawer = relationDB.getAllItemsOfDrawer(drawer);
        DrawerAdapter drawerAdapter = new DrawerAdapter(allItemsOfDrawer, id, requestCode, ids, DrawerActivity.this);
        drawerRecyclerView.setAdapter(drawerAdapter);

        addNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrawerActivity.this, ItemsActivity.class);
                intent.putExtra("flag",id);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(DrawerActivity.this, DrawersActivity.class));
    }
}