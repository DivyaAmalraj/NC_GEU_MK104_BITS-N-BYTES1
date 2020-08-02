package com.example.sih2020;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity {
    EditText mUsername,mEmail,mpassword;
    TextView mTextview3;
    Button mbutton_register;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_sign__up);

        mUsername =findViewById (R.id.edittext_fullname);
        mEmail=findViewById (R.id.edittext_Email);
        mpassword =findViewById (R.id.edittext_password);
        mTextview3 =findViewById (R.id.Textview3);
        mbutton_register =findViewById (R.id.button_register);

        fAuth=FirebaseAuth.getInstance ();

        if(fAuth.getCurrentUser ()!= null){
            startActivity (new Intent (getApplicationContext (),MainActivity.class));
        }
        mbutton_register.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                String email =mEmail.getText ().toString ().trim();
                String password= mpassword.getText ().toString ().trim ();

                if(TextUtils.isEmpty (email)){
                    mEmail.setError ("email is required");
                    return;
                }
                if(TextUtils.isEmpty (password)){
                    mpassword.setError ("password is reqiured");
                    return;
                }
                if(password.length()<6){
                    mpassword.setError("password must be >=6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword (email,password).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful ()){
                            Toast.makeText (Sign_Up.this,"user created..",Toast.LENGTH_SHORT).show();
                            startActivity (new Intent (getApplicationContext (),MainActivity.class));
                        }
                        else{
                            Toast.makeText (Sign_Up.this,"Error..!!!"+task.getException ().getMessage (),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        mTextview3.setOnClickListener (new View.OnClickListener (){
            @Override
            public void onClick(View v){
                startActivity (new Intent(getApplicationContext (),Login.class));
            }
        });
    }
}