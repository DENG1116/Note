package com.example.note.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.note.MainActivity;
import com.example.note.entity.Note;

import java.util.ArrayList;
import java.util.List;

public class CRUD {
    SQLiteOpenHelper dbHandler;  //数据库处理器
    SQLiteDatabase db; //定义数据库

    //取出数据库的数据
    private static final String[] columns = {
            NoteDatabase.ID,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME,
            NoteDatabase.MODE
    };

    //构造方法
    public CRUD(Context context) {
        //实例化SQLiteOpenHelper
        dbHandler = new NoteDatabase(context);
    }

    //对数据库进行写入功能
    public void open() {
        db = dbHandler.getWritableDatabase();
    }

    //关闭数据库
    public void close() {
        dbHandler.close();
    }

    //添加笔记,把note加入到database里面
    public Note addNote(Note note) {
        //判断笔记是否为空，为空则不添加
//        if(note.getContent().length()==0){
//            removeNote(note);
//            return null;
//        }
        //添加一个笔记到数据库
        //专门处理数据的一个类，相当于一个内容值
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDatabase.CONTENT, note.getContent());
        contentValues.put(NoteDatabase.TIME, note.getTime());
        contentValues.put(NoteDatabase.MODE, note.getTog());
        long insertId = db.insert(NoteDatabase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }

    //通过id获取Note数据
    public Note getNote(long id) {
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, columns, NoteDatabase.ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Note note = new Note(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        return note;
    }

    //通过id获取Note数据
    public List<Note> getAllNotes() {
        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, columns, null, null, null, null, null);
        List<Note> list = new ArrayList<>();
        Note note = null;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)));
                note.setTog(cursor.getInt(cursor.getColumnIndex(NoteDatabase.MODE)));
                list.add(note);
            }
        }
        return list;
    }

    //修改笔记
    public int updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteDatabase.CONTENT, note.getContent());
        values.put(NoteDatabase.TIME, note.getTime());
        values.put(NoteDatabase.MODE, note.getTog());
        return db.update(NoteDatabase.TABLE_NAME, values,
                NoteDatabase.ID + "=?", new String[]{String.valueOf(note.getId())});
    }

    //删除笔记
    public void removeNote(Note note) {
        db.delete(NoteDatabase.TABLE_NAME, NoteDatabase.ID + "=" + note.getId(), null);
    }

}
