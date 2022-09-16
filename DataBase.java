package com.example.basicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_AGE = "USER_AGE";
    public static final String COLUMN_USER_MALE = "USER_MALE";
    public static final String COLUMN_USER_FEMALE = "USER_FEMALE";
    public static final String COLUMN_USER_WEIGHT = "USER_WEIGHT";
    public static final String COLUMN_USER_HEIGHT = "USER_HEIGHT";
    public static final String COLUMN_ID = "ID";

    public DataBase(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    //called first time
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_AGE + " INT, " + COLUMN_USER_MALE + " BOOLEAN, " + COLUMN_USER_FEMALE + " BOOLEAN, " + COLUMN_USER_WEIGHT + " FLOAT, " + COLUMN_USER_HEIGHT + " FLOAT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    //called when new version is created
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userModel.getName());
        cv.put(COLUMN_USER_AGE, userModel.getAge());
        cv.put(COLUMN_USER_MALE, userModel.isMale());
        cv.put(COLUMN_USER_FEMALE, userModel.isFemale());
        cv.put(COLUMN_USER_WEIGHT, userModel.getWeight());
        cv.put(COLUMN_USER_HEIGHT, userModel.getHeight());

        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;

    }

    public boolean deleteOne(UserModel userModel){
        //find user model

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + userModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<UserModel> getAll(){

        List<UserModel> returnList = new ArrayList<>();

        //get data

        String queryString = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        //result
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                int userAge = cursor.getInt(2);
                boolean userMale = cursor.getInt(3) == 1 ? true: false;
                boolean userFemale = cursor.getInt(4) == 1 ? true: false;
                float userWeight = cursor.getFloat(5);
                float userHeight = cursor.getFloat(6);

                UserModel userModel = new UserModel(userID, userName, userAge, userMale, userFemale, userWeight, userHeight);
                returnList.add(userModel);

            }while(cursor.moveToNext());
        }
        else{
            // fail

        }

        // close
        cursor.close();
        db.close();
        return returnList;
    }


}
