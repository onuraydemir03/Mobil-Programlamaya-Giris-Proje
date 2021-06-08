package yildiz.edu.tr.onur.proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CombinesDB extends SQLiteOpenHelper {

    public CombinesDB(@Nullable Context context) {
        super(context, "combines.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE COMBINES(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "HATID INT, "+
                "GLASSESID INT, "+
                "UPPERBODYID INT, "+
                "LOWERBODYID INT, "+
                "SHOEID INT); ";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewCombine(Combine combine){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("hatid", combine.getHatItemID());
        cv.put("glassesid", combine.getGlassesItemID());
        cv.put("upperbodyid", combine.getUpperBodyItemID());
        cv.put("lowerbodyid", combine.getLowerBodyItemID());
        cv.put("shoeid", combine.getShoeItemID());
        long insert = db.insert("combines", null, cv);
        db.close();
        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<Combine> getAllCombines(){
        ArrayList<Combine> combines = new ArrayList<>();
        String query = "SELECT * FROM combines;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                int hatID = cursor.getInt(1);
                int glassesID = cursor.getInt(2);
                int upperID = cursor.getInt(3);
                int lowerID = cursor.getInt(4);
                int shoeID = cursor.getInt(5);
                Combine combine = new Combine(id, hatID,glassesID,upperID,lowerID,shoeID);
                combines.add(combine);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return combines;
    }

    public void deleteACombine(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("combines", "id = "+ id, null);
    }

    public Combine getACombineWID(int id){
        Combine combine = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM COMBINES WHERE ID = " + id+";";
        Cursor cursor = db.rawQuery(query,  null);
        if(cursor != null){
            cursor.moveToFirst();
            int hatID = cursor.getInt(1);
            int glassesID = cursor.getInt(2);
            int upperID = cursor.getInt(3);
            int lowerID = cursor.getInt(4);
            int shoeID = cursor.getInt(5);
            combine = new Combine(id, hatID,glassesID,upperID,lowerID,shoeID);
        }
        return combine;
    }

}
