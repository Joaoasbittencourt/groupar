package com.jotape.sisdic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jotape.sisdic.Modules.FirebaseWorker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TarefaProfiler extends AppCompatActivity {

    String atv_name;
    TextView personTxt,limitDateTxt,statusTxt,obsTxt;
    FirebaseAuth auth;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_profiler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        atv_name =  getIntent().getExtras().get("atv_name").toString();
        setTitle(atv_name);

        personTxt    = (TextView) findViewById(R.id.personTxt);
        limitDateTxt = (TextView) findViewById(R.id.limitDateTxt);
        statusTxt    = (TextView) findViewById(R.id.statusTxt);
        obsTxt       = (TextView) findViewById(R.id.obsTxt);

        //DATA LOAD ON TEXTS
        personTxt.setText(getIntent().getExtras().get("person").toString());
        limitDateTxt.setText(getIntent().getExtras().get("date").toString());
        statusTxt.setText(getIntent().getExtras().get("status").toString());




        //Tentando de forma RETARDADA pegar APENAS o UID do usuario para colocar no chat...
        //TODO: muda esta insanidade ---------------------------------------------------------------

        FirebaseUser user = auth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference membro = ref.child("Membros").child("Membros").child(uid);
        membro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Iterator data = dataSnapshot.getChildren().iterator();
                data.next();
                String text = (String) ((DataSnapshot)data.next()).getValue();
                username = text;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });





        // comentario
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(TarefaProfiler.this);
                builder.setTitle("Comente:");
                final EditText text = new EditText(TarefaProfiler.this);
                text.setHint("Comentário");
                builder.setView(text);

                final DatabaseReference tarefasRef = FirebaseDatabase.getInstance()
                        .getReference().child("Tarefas");

                final DatabaseReference mainRef = tarefasRef
                        .child("Tarefas").child(atv_name);


                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference obsRef = mainRef.child("obs");
                        String key  = obsRef.push().getKey();

                        DatabaseReference obs_root = obsRef.child(key);

                        Map<String,Object> pack = new HashMap<String, Object>();
                        pack.put("msg",text.getText().toString());
                        pack.put("user",username);

                        obs_root.updateChildren(pack);

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference obsRef = tarefasRef.child("Tarefas").child(atv_name).child("obs");

                    }
                }).show();


            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference obsRef = FirebaseDatabase.getInstance().getReference()
                .child("Tarefas")
                .child("Tarefas")
                .child(atv_name)
                .child("obs");

        obsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                appendChat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                appendChat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tarefa_profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        final DatabaseReference tarefasRef = FirebaseDatabase
                .getInstance().getReference().child("Tarefas");

        final DatabaseReference mainRef = tarefasRef
                .child("Tarefas").child(atv_name);

        if (id == R.id.designarItem)
        {

            Intent intent =  new Intent(getApplicationContext(),DesignarActivity.class);
            intent.putExtra("tarefa",atv_name);
            startActivity(intent);
        }

        if (id == R.id.finalizarItem)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(TarefaProfiler.this);
            builder.setTitle("Tem certeza que deseja Finalizar a Tarefa?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    mainRef.child("completed").setValue(true);
                    mainRef.child("status").setValue("Concluída");
                    finish();

                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();

        }

        if (id == R.id.deleteItem)
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(TarefaProfiler.this);
            builder.setTitle("Tem certeza que deseja apagar a Tarefa?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tarefasRef.child("Tarefas").child(atv_name).removeValue();
                    finish();
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseReference obsRef = tarefasRef.child("Tarefas").child(atv_name).child("obs");

                }
            }).show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void appendChat(DataSnapshot snap){


        Iterator i  = snap.getChildren().iterator();

        while(i.hasNext())
        {

            String text = (String) ((DataSnapshot)i.next()).getValue();
            String user = (String) ((DataSnapshot)i.next()).getValue();

            obsTxt.append(user+": "+text + " \n");

        }


    }

}
