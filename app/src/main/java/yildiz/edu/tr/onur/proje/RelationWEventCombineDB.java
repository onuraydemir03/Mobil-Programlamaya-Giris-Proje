package yildiz.edu.tr.onur.proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RelationWEventCombineDB extends SQLiteOpenHelper {
    Context context;
    public RelationWEventCombineDB(@Nullable Context context) {
        super(context, "relationWEventCombine.db", null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE relationWEventCombine(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EVENTID INT, COMBINEID INT);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addNewCombineToEvent(Event event, Combine combine){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("eventid", event.getId());
        cv.put("combineid", combine.getId());
        long insert = db.insert("relationWEventCombine", null, cv);
        db.close();
        if(insert == -1)
            return false;
        return true;
    }
    public ArrayList<Combine> getAllCombinesOfEvent(Event event){
        ArrayList<Combine> combines = new ArrayList<>();
        String query = "SELECT * FROM relationWEventCombine where eventid = "+ String.valueOf(event.getId()) + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CombinesDB combinesDB = new CombinesDB(this.context);
        Combine combine;
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                int combineID = cursor.getInt(2);
                combine = combinesDB.getACombineWID(combineID);
                combines.add(combine);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return combines;
    }

    public void deleteACombineFromEvent(int eventID, int combineID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("relationWEventCombine", "combineid = "+ combineID + " and eventid =  " + eventID, null);
    }

}
