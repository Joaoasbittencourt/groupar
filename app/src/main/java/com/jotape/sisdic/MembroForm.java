package com.jotape.sisdic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jotape.sisdic.Obj.Membro;
import com.jotape.sisdic.Obj.Tarefa;

public class MembroForm extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;



    Button cadastrarBtn;
    EditText nameEtxt,emailEtxt,phoneEtxt,senhaTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membro_form);

        database = FirebaseDatabase.getInstance();
        ref      = database.getReference();

        //Tarefas Reference:
        final DatabaseReference membrosRef = ref.child("Membros");

        cadastrarBtn = (Button) findViewById(R.id.cadastrarBtn);


        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameEtxt  = (EditText) findViewById(R.id.nameEtxt);
                emailEtxt = (EditText) findViewById(R.id.emailEtxt);
                phoneEtxt = (EditText) findViewById(R.id.phoneEtxt);
                senhaTxt = (EditText) findViewById(R.id.senhaTxt);

                Membro m = new Membro(nameEtxt.getText().toString(),emailEtxt.getText().toString(),phoneEtxt.getText().toString());

                try{
                    if(senhaTxt.getText().toString().equals("751920")){
                        membrosRef.child("Membros").push().setValue(m);
                        finish();
                    }else{
                        Toast.makeText(MembroForm.this, "Senha Incorreta", Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MembroForm.this, "O servidor da database não está disponínvel no momento" +
                            "tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
