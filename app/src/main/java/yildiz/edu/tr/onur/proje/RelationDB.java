package yildiz.edu.tr.onur.proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RelationDB extends SQLiteOpenHelper {
    Context context;
    public RelationDB(@Nullable Context context) {
        super(context, "relation.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE RELATION(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DRAWERID INT, ITEMID INT);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewItemToDrawer(Drawer drawer, Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("itemid", item.getId());
        cv.put("drawerid", drawer.getId());
        long insert = db.insert("relation", null, cv);
        db.close();
        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<Item> getAllItemsOfDrawer(Drawer drawer){
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM relation where drawerid = "+ String.valueOf(drawer.getId()) + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemsDB itemsDB = new ItemsDB(this.context);
        Item item;
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                int itemID = cursor.getInt(2);
                item = itemsDB.getAnItemWID(itemID);
                items.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    public void deleteAnItemFromDrawer(int drawerID, int itemID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("relation", "itemid = "+ itemID + " and drawerid =  " + drawerID, null);
    }
}
