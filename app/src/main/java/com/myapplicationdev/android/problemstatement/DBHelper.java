package com.myapplicationdev.android.problemstatement;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "song.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG_TITLE = "title";
    private static final String COLUMN_SONG_SINGERS = "singers";
    private static final String COLUMN_SONG_YEAR = "year";
    private static final String COLUMN_SONG_STARS = "stars";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG_TITLE + " TEXT ," + COLUMN_SONG_SINGERS + " TEXT ,"
                + COLUMN_SONG_YEAR + " INTEGER ," + COLUMN_SONG_STARS + " INTEGER ) ";
        db.execSQL(createNoteTableSql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN module_name TEXT ");
    }
    public long insertSong(String title, String singers, Integer year, Integer starts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, title);
        values.put(COLUMN_SONG_SINGERS, singers);
        values.put(COLUMN_SONG_YEAR, year);
        values.put(COLUMN_SONG_STARS,starts);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> notes = new ArrayList<Song>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_SINGERS + ", " + COLUMN_SONG_YEAR + ", " +  COLUMN_SONG_STARS
                + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                Integer year = cursor.getInt(3);
                Integer starts = cursor.getInt(4);
                Song note = new Song( id, title, singers, year, starts);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG_TITLE, data.getTitle());
        values.put(COLUMN_SONG_SINGERS, data.getSingers());
        values.put(COLUMN_SONG_YEAR, data.getYear());
        values.put(COLUMN_SONG_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }


    public ArrayList<Song> getAllSong(String keyword) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SONG_TITLE, COLUMN_SONG_SINGERS, COLUMN_SONG_YEAR, COLUMN_SONG_STARS};
        String condition = COLUMN_SONG_STARS + " = ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);

                Song song = new Song(id, title,singers,year,star);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Song> getAllSongByYear(String keyword) {
        ArrayList<Song> songs = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SONG_TITLE, COLUMN_SONG_SINGERS, COLUMN_SONG_YEAR, COLUMN_SONG_STARS};
        String condition = COLUMN_SONG_YEAR + " Like ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);

                Song song = new Song(id, title,singers,year,star);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public boolean isExistingSong(String title) {
        // Select all the notes' content
        String selectQuery = "SELECT " + COLUMN_SONG_TITLE + " FROM "
                + TABLE_SONG + " WHERE " + COLUMN_SONG_TITLE + " = '"
                + title + "'";
        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);
        // moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            return true;
        }
        // Close connection
        cursor.close();
        db.close();

        return false;
    }
}
