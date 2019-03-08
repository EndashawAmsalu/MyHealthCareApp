package com.example.endesh.myhealthcareapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.endesh.myhealthcareapp.Asset.Asset;
import com.example.endesh.myhealthcareapp.Broadcast.MainBroadcast;
import com.example.endesh.myhealthcareapp.Broadcast.MyInternet;
import com.example.endesh.myhealthcareapp.EasyNote.MainActivity;
import com.example.endesh.myhealthcareapp.Service.MainService;
import com.example.endesh.myhealthcareapp.RegistrationForm.Login;

public class HomeScreen extends AppCompatActivity {
    TextView mobile;
    Button fire,room;
    MenuItem internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void registrationForm(View view) {
        Intent i = new Intent(HomeScreen.this,Login.class);
        startActivity(i);
    }

    public void documentHelp(View view) {
        Intent i= new Intent(HomeScreen.this,Asset.class);
        startActivity(i);
    }

   /* public void note(View view) {
        Intent ii= new Intent(Cover.this,MainActivity.class);
        startActivity(ii);
    }*/
   public void note(View view) {
       Intent i = new Intent(HomeScreen.this,MainActivity.class);
       startActivity(i);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu2,menu);
        //MenuItem menuItem = menu.findItem(R.id.action_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.service){
            Intent intent = new Intent(HomeScreen.this,MainService.class);
            startActivity(intent);
        }
        else if ( id == R.id.broadcast){
            Intent intent = new Intent(HomeScreen.this,MainBroadcast.class);
            startActivity(intent);
        }
        else if ( id == R.id.internet){
            Intent intent = new Intent(HomeScreen.this,MyInternet.class);
            startActivity(intent);
        }
        else if (id == R.id.exit){
            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
            builder.setTitle("Exit");
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("Yes. Exit now!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
