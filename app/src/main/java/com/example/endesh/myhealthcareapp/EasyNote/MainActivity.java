package com.example.endesh.myhealthcareapp.EasyNote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.endesh.myhealthcareapp.Asset.Asset;
import com.example.endesh.myhealthcareapp.HomeScreen;
import com.example.endesh.myhealthcareapp.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {

    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private NoteListAdapter noteListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    MaterialSearchView searchView;
    private List<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
          //for the search icon
        searchView = findViewById(R.id.search_view);//*

        //Recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        //for search icon
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        names = Arrays.asList(getResources().getResourceName(R.id.recyclerview));


        noteListAdapter = new NoteListAdapter(this,this);
        //In order to display items in the list, call setAdapter to associate an adapter with the list.
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //call the floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        //The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way
        //ViewModel objects are scoped to the Lifecycle passed to the ViewModelProvider when getting the ViewModel
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {

                noteListAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            //code to insert note
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);

            Toast.makeText(getApplicationContext(),
                    R.string.saved,
                    Toast.LENGTH_LONG).show();
        }
        else if(requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            //code to update the note
            Note note = new Note(
                    data.getStringExtra(EditNoteActivity.NOTE_ID),
                    data.getStringExtra(EditNoteActivity.UPDATED_NOTE));
            noteViewModel.update(note);

            Toast.makeText(getApplicationContext(),
                    R.string.updated,
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(
                    getApplicationContext(),
                    R.string.not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        //Delete operation
        noteViewModel.delete(myNote);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.back){
            Intent intent = new Intent(MainActivity.this,HomeScreen.class);
            startActivity(intent);
            }
        else if ( id == R.id.document){
            Intent intent = new Intent(MainActivity.this,Asset.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
