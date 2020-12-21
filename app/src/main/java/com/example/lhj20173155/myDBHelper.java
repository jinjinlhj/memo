package com.example.lhj20173155;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {

    public myDBHelper(Context context){
        super(context, "memoDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memoTBL (iNumber INTEGER PRIMARY KEY AUTOINCREMENT, sTitle TEXT, sContent TEXT, sDate TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS memoTBL");
        onCreate(db);
    }
}