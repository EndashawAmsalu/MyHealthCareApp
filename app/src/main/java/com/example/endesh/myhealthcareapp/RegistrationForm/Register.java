package com.example.endesh.myhealthcareapp.RegistrationForm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.endesh.myhealthcareapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText fname, lname, email,pword, cpword;
    Button register,cancel;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        pword = findViewById(R.id.pword);
        cpword = findViewById(R.id.cofirm);
        cancel = findViewById(R.id.cancel);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        register = findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String NAME = fname.getText().toString();
                final String LASTNAME = lname.getText().toString();
                final String EMAIL = email.getText().toString();
                final String PASSWORD = pword.getText().toString();
                String CONFIRM = cpword.getText().toString();
                if(NAME.isEmpty()|| LASTNAME.isEmpty()|| EMAIL.isEmpty()||
                PASSWORD.isEmpty()|| !CONFIRM.equals(PASSWORD))
                {
                    Toast.makeText(getApplicationContext(),"Please enter right details"
                    ,Toast.LENGTH_SHORT).show();

                }else{

                    auth.createUserWithEmailAndPassword(EMAIL,PASSWORD)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // loading.setVisibility(View.INVISIBLE);
                                    Users user = new Users();
                                    user.setEmail(EMAIL);
                                    user.setPword(PASSWORD);
                                    user.setFname(NAME);
                                    user.setLname(LASTNAME);



                                    //user email to key
                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                            setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(),"Registeration successfull",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent welcome = new Intent(Register.this, Login.class);
                                            startActivity(welcome);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // loading.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getApplicationContext(),"Registeration failed"+e.getMessage(),Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //loading.setVisibility(View.INVISIBLE);
                            Toast.makeText (getApplicationContext(),"Registeration failed"
                                    +e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });


                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
