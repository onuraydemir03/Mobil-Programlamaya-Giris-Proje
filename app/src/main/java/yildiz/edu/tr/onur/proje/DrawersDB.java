package yildiz.edu.tr.onur.proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawersDB extends SQLiteOpenHelper {

    public DrawersDB(@Nullable Context context) {
        super(context, "drawers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE DRAWERS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewDrawer(Drawer drawer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", drawer.getName());
        long insert = db.insert("drawers", null, cv);
        db.close();
        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<Drawer> getAllDrawers(){
        ArrayList<Drawer> drawers = new ArrayList<>();
        String query = "SELECT * FROM drawers;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Drawer drawer = new Drawer(id,name);
                drawers.add(drawer);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return drawers;
    }

    public void deleteADrawer(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("drawers", "id = "+ id, null);
    }

    public Drawer getADrawerWID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM DRAWERS WHERE ID = " + id+";";
        Cursor cursor = db.rawQuery(query,  null);
        if(cursor != null)
            cursor.moveToFirst();
        String name = cursor.getString(1);
        Drawer drawer = new Drawer(id,name);
        return drawer;
    }


}
