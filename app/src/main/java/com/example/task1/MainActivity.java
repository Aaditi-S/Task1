package com.example.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView register;
    private EditText editTextEmail , editTextPassword;
    private Button signIn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView) findViewById(R.id.registerLogin);
        register.setOnClickListener((View.OnClickListener) this);

        mAuth = FirebaseAuth.getInstance();

        signIn=(Button) findViewById(R.id.Login);
        signIn.setOnClickListener(this);

        editTextEmail=(EditText) findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.registerLogin:
                startActivity(new Intent(this,Register.class));
            break;
            case R.id.Login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        final String email=editTextEmail.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
    //validations of email and password
        if(email.isEmpty())
        {
            editTextEmail.setError("Please enter Email");
            editTextEmail.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editTextPassword.setError("password should be of minimum length 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                      {
                                          @Override
                                          public void onComplete(@NonNull Task<AuthResult> task)
                                          {
                                              if(task.isSuccessful())
                                              {
                                                  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                  if(user.isEmailVerified()){
                                                      startActivity(new Intent(MainActivity.this ,Logout.class));
                                                  }
                                                  else{
                                                      user.sendEmailVerification();
                                                      Toast.makeText(MainActivity.this,"Check email and verifiy it",Toast.LENGTH_LONG).show();

                                                  }



                                              }
                                              else
                                              {
                                                  Toast.makeText(MainActivity.this,"Failed to Login! Please check credentials",Toast.LENGTH_LONG).show();
                                                  progressBar.setVisibility(View.GONE);
                                              }
                                          }
                                      }
                );


    }
}
