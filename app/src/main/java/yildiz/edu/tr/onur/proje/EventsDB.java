package yildiz.edu.tr.onur.proje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventsDB extends SQLiteOpenHelper {
    public EventsDB(@Nullable Context context) {
        super(context, "events.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE EVENTS(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "TYPE TEXT, " +
                "DATE TEXT, " +
                "L1 REAL, " +
                "L2 REAL);";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addNewEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", event.getName());
        cv.put("type", event.getType());
        cv.put("date", event.getDate());
        cv.put("l1", event.getL1());
        cv.put("l2", event.getL2());
        long insert = db.insert("events", null, cv);
        db.close();
        if(insert == -1)
            return false;
        return true;
    }

    public ArrayList<Event> getAllEvents(){
        ArrayList<Event> events = new ArrayList<>();
        String query = "SELECT * FROM EVENTS;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String date = cursor.getString(3);
                long l1 = cursor.getLong(4);
                long l2 = cursor.getLong(5);
                Event event = new Event(id, name, type, date, l1, l2);
                events.add(event);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    public void deleteAnEvent(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("events", "id = "+ id, null);
    }

    public Event getAnEventWID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM EVENTS WHERE ID = " + id+";";
        Cursor cursor = db.rawQuery(query,  null);
        if(cursor.moveToFirst()){
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String date = cursor.getString(3);
            long l1 = cursor.getLong(4);
            long l2 = cursor.getLong(5);
            Event event = new Event(id, name, type, date, l1, l2);
            return event;
        }
        return null;
    }

    public int getLastID()
    {
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT MAX(id) FROM events;", null);
        int maxid = (cursor.moveToFirst() ? cursor.getInt(0) : 0);
        return maxid;
    }

    public void changeAnEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", event.getName());
        cv.put("type", event.getType());
        cv.put("date", event.getDate());
        cv.put("l1", event.getL1());
        cv.put("l2", event.getL2());
        db.update("events",cv,"ID" + "=" + event.getId(),null);
        db.close();
    }


}
