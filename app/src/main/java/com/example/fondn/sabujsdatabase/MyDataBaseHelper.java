package com.example.fondn.sabujsdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASENAME = "Student";
    private static final String TABLE_NAME = "Student_Details";
    private static final String NAME = "Name";
    private static final String ID = "_id";
    private static final String AGE = "age";
    private static final String GENDER = "Gender";
    private static final int VERSION = 3;
    private static final String CREATEDATABASE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VERCHAR(255)," + AGE + " INTEGER," + GENDER + " VERCHAR);";
    private static final String DROPTABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECTDATA = "SELECT * FROM " + TABLE_NAME;
    private Context context;

    public MyDataBaseHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context, "on Create Database", Toast.LENGTH_SHORT).show();
        try {
            db.execSQL(CREATEDATABASE);
            Toast.makeText(context, "Successfully Database Created ..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Exception Found : " + e, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "On Upgrade Call", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Its Updated", Toast.LENGTH_SHORT).show();
            db.execSQL(DROPTABLE);
            onCreate(db);


        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public long insertData(String name, String age, String gender) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(GENDER, gender);
        long rowid = database.insert(TABLE_NAME, null, contentValues);
        return rowid;


    }

    public Cursor showDataFromDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(SELECTDATA, null);
        return cursor;
    }

    public boolean updateData(String id,String name,String age,String gender){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(GENDER, gender);
         database.update(TABLE_NAME,contentValues,ID+" = ?",new String[]{id});
        return true;
    }

    public int deleteData(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,ID+" = ?",new String[] {id});

    }


}
