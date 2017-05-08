package com.jotape.sisdic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText emailEtxt,passwordEtxt;
    Button submitBtn,signUpBtn;
    private FirebaseAuth Auth;
    FirebaseAuth.AuthStateListener AuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Auth = FirebaseAuth.getInstance();

        emailEtxt    = (EditText) findViewById(R.id.emailEtxt);
        passwordEtxt = (EditText) findViewById(R.id.passwordEtxt);
        submitBtn    = (Button) findViewById(R.id.submitBtn);
        signUpBtn    = (Button) findViewById(R.id.signUpBtn);

        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){

                    Intent goToMain = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(goToMain);

                }
            }
        };

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSignIn();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();

        Auth.addAuthStateListener(AuthListener);

    }
    @Override
    public void onBackPressed() {
    }

    private void startSignIn(){

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setIndeterminate(true);
        progress.show();

        String email = emailEtxt.getText().toString();
        String password = passwordEtxt.getText().toString();

        Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progress.dismiss();

                if(task.isSuccessful()){

                    Toast.makeText(Login.this, "Logado em Equipe UFBA IBRACON ", Toast.LENGTH_SHORT).show();

                }else if(!task.isSuccessful()){

                    Toast.makeText(Login.this, "Senha ou usuário incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
