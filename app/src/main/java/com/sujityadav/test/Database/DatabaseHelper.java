package com.sujityadav.test.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sujityadav.test.Model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sujit yadav on 1/26/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper implements ChatListener{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CategoryDatabase.db";
    private static final String KEY_ID = "id";
    private static final String All_MESSAGE_TABLE = "all_message_table";
    private static final String KEY_BODY = "body";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_URL = "imageUrl";
    private static final String KEY_MESSAGE_TIME = "messageTime";
    private static final String KEY_FAV = "fav";

    String CREATE_TABLE = "CREATE TABLE "+All_MESSAGE_TABLE+" ("+KEY_ID+" INTEGER PRIMARY KEY," +
            ""+KEY_BODY+" TEXT,"+
            ""+KEY_USERNAME+" TEXT,"+
            ""+KEY_NAME+" TEXT,"+
            ""+KEY_IMAGE_URL+" TEXT,"+
            ""+KEY_MESSAGE_TIME+" TEXT,"+
            ""+KEY_FAV+" INTEGER)";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ignore for this project
    }

    @Override
    public void AddChatList(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();

            values.put(KEY_BODY, message.body);
            values.put(KEY_USERNAME, message.username);
            values.put(KEY_NAME, message.name);
            values.put(KEY_IMAGE_URL, message.imageUrl);
            values.put(KEY_MESSAGE_TIME, message.messageTime);
            values.put(KEY_FAV, 0);
            db.insert(All_MESSAGE_TABLE, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }

    }

    @Override
    public ArrayList<Message> GetMessageList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Message> messages = null;
        try{
            messages = new ArrayList<>();
            String QUERY = "SELECT * FROM "+All_MESSAGE_TABLE;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    Message data = new Message();
                    data.body=cursor.getString(1);
                    data.username=cursor.getString(2);
                    data.name=cursor.getString(3);
                    data.imageUrl=cursor.getString(4);
                    data.messageTime=cursor.getString(5);
                    data.fav=cursor.getInt(6);
                    messages.add(data);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return messages;
    }

    @Override
    public int GetFavCount(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Message> messages = null;
        try{
            messages = new ArrayList<>();
            String QUERY = "SELECT * FROM "+All_MESSAGE_TABLE+" WHERE "+KEY_NAME+ "='" + name + "'"+" AND "+KEY_FAV+ " = "+1;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    Message data = new Message();
                    data.body=cursor.getString(1);
                    data.username=cursor.getString(2);
                    data.name=cursor.getString(3);
                    data.imageUrl=cursor.getString(4);
                    data.messageTime=cursor.getString(5);
                    data.fav=cursor.getInt(6);
                    messages.add(data);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return messages.size();
    }

    @Override
    public int GetChatCount(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Message> messages = null;
        try{
            messages = new ArrayList<>();
            String QUERY = "SELECT * FROM "+All_MESSAGE_TABLE+" WHERE "+KEY_NAME+  "='" + name + "'";
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    Message data = new Message();
                    data.body=cursor.getString(1);
                    data.username=cursor.getString(2);
                    data.name=cursor.getString(3);
                    data.imageUrl=cursor.getString(4);
                    data.messageTime=cursor.getString(5);
                    data.fav=cursor.getInt(6);
                    messages.add(data);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return messages.size();
    }

    @Override
    public void updateFav(String msgTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_FAV, 1);
        db.update(All_MESSAGE_TABLE, cv, KEY_MESSAGE_TIME + "= ?", new String[] {msgTime});
        db.close();
    }
}
