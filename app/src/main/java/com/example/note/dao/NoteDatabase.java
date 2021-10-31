package com.example.note.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class NoteDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String ID = "_id";
    public static final String TIME = "time";
    public static final String MODE = "mode";

    public NoteDatabase(Context context) {
        super(context, "notes", null, 1);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CONTENT + " TEXT NOT NULL, "
                + TIME + " TEXT NOT NULL, "
                + MODE + " INTEGER DEFAULT 1)");

    }

    /**
     * 检测已有的版本
     *
     * @param db         指向的数据库
     * @param oldVersion 检测已有的版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //从最小的版本开始，向最新的版本循环，保证每一步都更新
       /* for (int i = oldVersion; i < newVersion; i++) {
            switch (i){
                case 1:break;
                case 2:updateMode(db);
                default:break;
            }
        }*/
    }
}
