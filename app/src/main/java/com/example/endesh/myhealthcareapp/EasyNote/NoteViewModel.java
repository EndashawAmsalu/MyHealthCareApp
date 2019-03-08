package com.example.endesh.myhealthcareapp.EasyNote;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(Application application) {
        super(application);

        noteDB = NoteRoomDatabase.getDatabase(application);
        noteDao = noteDB.noteDao();
        mAllNotes = noteDao.getAllNotes();

    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);

    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        //constructor
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

/*    private class OperationsAsyncTask extends AsyncTask<Note, Void, Void>{

        NoteDao mAsyncTaskDao;
        OperationsAsyncTask(NoteDao dao){
            this.mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }*/

    private class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao mNoteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insert(notes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao mNoteDao;

        UpdateAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Note, Void, Void>{


        NoteDao mNoteDao;
        public DeleteAsyncTask(NoteDao noteDao) {
            this.mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes[0]);
            return null;
        }
    }
}

