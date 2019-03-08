package com.example.endesh.myhealthcareapp.EasyNote;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Note.class, version = 1,exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {
    //abstract method for each Dao related to it
    public abstract NoteDao noteDao();
    //a single instance of db to ensure that our db class should be singleton
    //singeleton object
    //instance for roomDB
    private static volatile NoteRoomDatabase noteRoomInstance;
    //acquire instance of DB using Room.databaseBuilder()
    static NoteRoomDatabase getDatabase(final Context context){
        if(noteRoomInstance ==null){
            synchronized (NoteRoomDatabase.class){
                if(noteRoomInstance==null){
                    noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class,"note_database").build();
                }
            }
        }
        return noteRoomInstance;
    }
}
