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
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private TextView register ,banner,registerUser;
    private EditText editTextUserName,editTextEmail , editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register=(TextView) findViewById(R.id.goToLoginBtn);
        register.setOnClickListener((View.OnClickListener) this);
        mAuth = FirebaseAuth.getInstance();
        banner= (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        registerUser=(Button) findViewById(R.id.Login);
        registerUser.setOnClickListener(this);
        editTextUserName=(EditText) findViewById(R.id.userName);
        editTextEmail=(EditText) findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.Login:
                registerUser();
                break;
            case R.id.goToLoginBtn:
                startActivity(new Intent(this,MainActivity.class));
                break;

        }
    }

    private void registerUser()
    {
        final String email=editTextEmail.getText().toString().trim();
        final String userName=editTextUserName.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();

        if(userName.isEmpty())
        {
            editTextUserName.setError("Please enter user name");
            editTextUserName.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editTextEmail.setError("Please enter Email");
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

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter valid email");
            editTextEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            User user =new User(userName,email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()
                                    ).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Register.this,"User has been registered Successfully!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this,"Falied to Register!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });


                        }
                        else
                        {
                            Toast.makeText(Register.this,"User already registered",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
        );



    }


}