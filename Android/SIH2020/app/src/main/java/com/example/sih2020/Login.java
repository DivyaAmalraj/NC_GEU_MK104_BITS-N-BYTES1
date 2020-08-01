package com.example.sih2020;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mEmail,mpassword;
    Button mlogin;
    TextView mTextview6;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ( ).setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_login);

        mEmail =findViewById (R.id.edittext_Email);
        mpassword =findViewById (R.id.edittext_password);
        mTextview6 =findViewById (R.id.Textview6);
        fAuth =FirebaseAuth.getInstance ();

        mlogin =findViewById (R.id.button_login);
        mlogin.setOnClickListener (new View.OnClickListener (){
            @Override
            public void onClick(View v){
                String email = mEmail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty (password)){
                    mpassword.setError("Password is required");
                    return;
                }
                if(password.length ()<6){
                    mpassword.setError("Password must be >=6 characters.");
                    return;
                }
                fAuth.signInWithEmailAndPassword (email,password).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful ()){
                            Toast.makeText (Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show ();
                            startActivity (new Intent(getApplicationContext (), MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this,"Error..!!!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mTextview6.setOnClickListener (new View.OnClickListener (){
            @Override
            public void onClick(View v){
                startActivity (new Intent(getApplicationContext (), Sign_Up.class));
            }
        });
    }
}