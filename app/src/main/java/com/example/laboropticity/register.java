package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    Button RegBTN;
    TextInputEditText EmailRtv,PasswordRtv;

    FirebaseAuth mAuth;

    ProgressBar Pbar;

    TextView LoginNow;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth= FirebaseAuth.getInstance();

        EmailRtv=findViewById(R.id.EmailRtv);
        PasswordRtv=findViewById(R.id.PasswordRtv);
        RegBTN=findViewById(R.id.RegBTN);
        Pbar=findViewById(R.id.Pbar);
        LoginNow=findViewById(R.id.LoginNow);


        // Get a reference to the string array that we just created
        String[] languages = getResources().getStringArray(R.array.Gender);

// Create an ArrayAdapter and pass the required parameters: context, drop down layout, and the array.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, languages);

// Get a reference to the AutoCompleteTextView
        AutoCompleteTextView autocompleteTV = findViewById(R.id.autoCompleteTextView);

// Set the adapter to the AutoCompleteTextView
        autocompleteTV.setAdapter(arrayAdapter);


        LoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });



        RegBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pbar.setVisibility(View.VISIBLE);
                String email,password;

                email= String.valueOf(EmailRtv.getText());
                password=String.valueOf(PasswordRtv.getText());


                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(email))
                {
                    Toast.makeText(register.this, "Email/Password Cannot Be Empty", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Pbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(register.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }
}