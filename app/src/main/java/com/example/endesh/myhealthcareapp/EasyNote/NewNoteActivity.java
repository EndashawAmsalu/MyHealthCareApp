package com.example.endesh.myhealthcareapp.EasyNote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.endesh.myhealthcareapp.R;

public class NewNoteActivity extends AppCompatActivity {

    public static final String NOTE_ADDED = "new_note";
    private EditText etNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        etNewNote = findViewById(R.id.etNewNote);

        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent resultIntent = new Intent();
                //if edit text is empty we set the result as cancelled
                if(TextUtils.isEmpty(etNewNote.getText())){
                    setResult(RESULT_CANCELED, resultIntent);
                }
                //else we will fetch the note
                else{
                    String note = etNewNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED, note);
                    setResult(RESULT_OK,resultIntent);
                }
                finish();

            }
        });
    }
}
