package com.example.ht_well;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//variables needed for database
public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "noteDB";
    private static final String DATABASE_TABLE = "noteTable";

    public NoteDatabase(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }
    //columns for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";


    // CREATE database, with given columns (id INT PRIMARY KEY, title TEXT, content TEXT, date TEXT, time TEXT);
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = "CREATE TABLE " + DATABASE_TABLE + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TITLE + " TEXT," +
                KEY_CONTENT + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT" + ")";

        db.execSQL(createDB);
    }

    // If a newer version database exists. Continues with newer database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    //will create a long with addNote note paramaters.
    //Gives a note new values and inserts it into the database.
    public long addNote (Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contVal = new ContentValues();
        contVal.put(KEY_TITLE,note.getTitle());
        contVal.put(KEY_CONTENT,note.getContent());
        contVal.put(KEY_DATE,note.getDate());
        contVal.put(KEY_TIME,note.getTime());

        //Inserts created long into the database. returns the ID.
        long ID = db.insert(DATABASE_TABLE,null,contVal);
        Log.d("inserted", "ID --> " + ID);
        return ID;

    }

    //method gets a note from database. cursor is created for accessing database items.
    //Cursor is assigned to the first index on the list.
    //Will save for cursor index: 0 long = ID, 1 = title, 2 = details, 3 = date, 4 = time
    //as a parameter an id is taken
    //returns a note from the database with given attributes
    public Note getNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME};
        Cursor cursor = db.query(DATABASE_TABLE,query, KEY_ID+"=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Note(
                Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
    }

    // creates a new arraylist for notes. Gets notes and sets them into an Arraylist.
    //select * from databasename
    //Will use a query to access database. Indexes for query defined earlier, will return a list of all notes saved.
    //using a cursor, doWhile passes through all items in the database and adds them into an arraylist.
    public List<Note> getNotes() {
        List<Note> allNotes = new ArrayList<>();
        String query = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setID(cursor.getInt(0));
                //System.out.println(cursor.getColumnIndex("id"));
                System.out.println(cursor.getInt(0));
                //System.out.println(note.getID());
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));
                allNotes.add(note);
            }while(cursor.moveToNext());
        }
        return allNotes;
    }
    // method for editing existing entries in the database.
    //accesses database that exists
    //uses methods from note class to get note details.
    // takes note as parameter and returns an updated database.
    public int editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contVal = new ContentValues();
        Log.d("Edited", "Edited Title: -> "+ note.getTitle() + "\n ID -> "+note.getID());
        contVal.put(KEY_TITLE,note.getTitle());
        contVal.put(KEY_CONTENT,note.getContent());
        contVal.put(KEY_DATE,note.getDate());
        contVal.put(KEY_TIME,note.getTime());
        return db.update(DATABASE_TABLE,contVal,KEY_ID+"=?",new String[]{String.valueOf(note.getID())});
    }

    //method for deleting item from database. Takes item name and key and deletes it.
    void deleteNote(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
