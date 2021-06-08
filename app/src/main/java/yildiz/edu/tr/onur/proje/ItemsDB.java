package yildiz.edu.tr.onur.proje;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class ItemsDB extends SQLiteOpenHelper {
    Context context;
    public ItemsDB(@Nullable Context context) {
        super(context, "items.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE ITEMS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "KIND TEXT, "+
                "COLOR TEXT, "+
                "PATTERN TEXT, "+
                "DATE TEXT, "+
                "PRICE INT, "+
                "IMAGE TEXT);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kind", item.getKind());
        cv.put("color", item.getColor());
        cv.put("pattern", item.getPattern());
        cv.put("date", item.getBuyingDate());
        cv.put("price", item.getPrice());
        cv.put("image", item.getImgName());

        long insert = db.insert("items", null, cv);
        db.close();
        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<Item> getAllItems(){
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String kind = cursor.getString(1);
                String color = cursor.getString(2);
                String pattern = cursor.getString(3);
                String date = cursor.getString(4);
                int price = cursor.getInt(5);
                String image = cursor.getString(6);
                Item item = new Item(id,kind,color,pattern,image,date,price);
                items.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }
    public void deleteAnItem(int id){
        CombinesDB combinesDB = new CombinesDB(context);
        ArrayList<Combine> combines = combinesDB.getAllCombines();
        for(Combine combine : combines){
            if(combine.getHatItemID() == id || combine.getGlassesItemID() == id || combine.getUpperBodyItemID() == id || combine.getLowerBodyItemID() == id || combine.getShoeItemID() == id){
                combinesDB.deleteACombine(combine.getId());
            }
        }
        RelationDB relationDB = new RelationDB(context);
        DrawersDB drawersDB = new DrawersDB(context);
        ArrayList<Drawer> drawers = drawersDB.getAllDrawers();
        for(Drawer drawer : drawers){
            ArrayList<Item> items = relationDB.getAllItemsOfDrawer(drawer);
            Item item = getAnItemWID(id);
            System.out.println("Items : " + items.toString());
            System.out.println("Item : " + item.toString());
            for(Item item_1 : items){
                if(item.toString().equals(item_1.toString())){
                    relationDB.deleteAnItemFromDrawer(drawer.getId() , item_1.getId());
                }
            }
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("items", "id = "+ id, null);
    }

    public int getLastID()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT MAX(id) FROM items;", null);
        int maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
        return maxid;
    }

    public Item getAnItemWID(int id){
        Item item = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ITEMS WHERE ID = " + id+";";
        Cursor cursor = db.rawQuery(query,  null);
        if(cursor != null){
            cursor.moveToFirst();
            String kind = cursor.getString(1);
            String color = cursor.getString(2);
            String pattern = cursor.getString(3);
            String date = cursor.getString(4);
            int price = cursor.getInt(5);
            String image = cursor.getString(6);
            item = new Item(id,kind,color,pattern,image,date,price);
        }
        return item;
    }
    public void changeAnItem(int id, Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kind", item.getKind());
        cv.put("color", item.getColor());
        cv.put("pattern", item.getPattern());
        cv.put("date", item.getBuyingDate());
        cv.put("price", item.getPrice());
        cv.put("image", item.getImgName());
        db.update("items",cv,"ID" + "=" + id,null);
        db.close();
    }

}
