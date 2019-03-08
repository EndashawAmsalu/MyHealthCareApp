package com.example.endesh.myhealthcareapp.EasyNote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.endesh.myhealthcareapp.R;

public class EditNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    public static final String UPDATED_NOTE = "note_text";
    private EditText etNote;
    private Bundle bundle;
    private String noteId;
    private LiveData<Note> note;
    EditNoteViewModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etNote = findViewById(R.id.etNote);

        //to display the information in the bundle
        bundle = getIntent().getExtras();
        if (bundle != null){
            noteId = bundle.getString("note_id");
        }

        noteModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        note = noteModel.getNote(noteId);
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                etNote.setText(note.getNote());
            }
        });

    }

    //update the note after clicking the edit button
    public void updateNote(View view) {
        String updatedNote = etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID, noteId);
        resultIntent.putExtra(UPDATED_NOTE, updatedNote);
        setResult(RESULT_OK,resultIntent);
        finish();
    }
    //cancel the note if we don't want the edit
    public void cancelUpdate(View view) {
        //This method stops the currently-running activity
        finish();
    }
}
