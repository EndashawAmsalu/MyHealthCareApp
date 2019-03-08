package com.example.endesh.myhealthcareapp.RegistrationForm;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.endesh.myhealthcareapp.R;

public class MainRegisterActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CONTACT = 0;
    private static final int PICK_CONTACT = 1;
    EditText nam, patientID, age, cntctDetail;
    Button cntctButton, update, delete, retrieve, addData;
    Database myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        nam = findViewById(R.id.editText);
        patientID = findViewById(R.id.editText4);
        age = findViewById(R.id.editText2);
        cntctDetail = findViewById(R.id.editText3);
        cntctButton = findViewById(R.id.button_contact);
        update = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_Delete);
        retrieve = findViewById(R.id.button_retrive);
        addData = findViewById(R.id.button_addData);
        myDb = new Database(this);
    }

    public void update(View view) {
        boolean isUpdate = myDb.updateData(patientID.getText().toString(),
                nam.getText().toString(),
                age.getText().toString(), cntctDetail.getText().toString());
        if (isUpdate == true)
            Toast.makeText(MainRegisterActivity.this, "Data Update", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainRegisterActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
    }

    public void delete(View view) {
        Integer deletedRows = myDb.deleteData(patientID.getText().toString());
        if (deletedRows > 0)
            Toast.makeText(MainRegisterActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainRegisterActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
    }

    public void retrieve(View view) {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Id :" + res.getString(0) + "\n");
            buffer.append("Patient Name :" + res.getString(3) + "\n");
            buffer.append("Age :" + res.getString(2) + "\n");
            buffer.append("Diagnosis Detail :" + res.getString(1) + "\n\n");
        }

        // Show all data
        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void addData(View view) {
        boolean isInserted = myDb.insertData(nam.getText().toString(),
                age.getText().toString(),
                cntctDetail.getText().toString());
        if (isInserted == true)
            Toast.makeText(MainRegisterActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainRegisterActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    /////permissions////////
    public void contactPermision(View v) {
        askForContactPermission();
    }
    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //To check if you have a permission
            if (ContextCompat.checkSelfPermission(MainRegisterActivity.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {

                //receiving the results for permission requests
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainRegisterActivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    //Creates a builder for an alert dialog that uses the default alert dialog theme
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainRegisterActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        //@TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();

                } else {

                    ActivityCompat.requestPermissions(MainRegisterActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);
                }
            } else {
                getContact();
            }
        }
    }
    private void getContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                    // permission was granted
                } else {
                    Toast.makeText(MainRegisterActivity.this, "No permission for contacts",
                            Toast.LENGTH_SHORT).show();
                    // permission denied
                }
                break;
            }

        }
    }
    //When the user is done with the subsequent activity and returns, the system calls your activity's onActivityResult() method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case(PICK_CONTACT):

                if(resultCode == RESULT_OK && requestCode== PICK_CONTACT){
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.getCount() > 0) {
                        while (c.moveToNext()) {
                            String name=c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            cntctDetail.setText(name);

                        }
                    }
                }
        }
    }

}
