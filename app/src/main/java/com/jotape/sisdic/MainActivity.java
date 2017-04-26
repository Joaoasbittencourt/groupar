package com.jotape.sisdic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jotape.sisdic.CustomAdapters.MembroAdapter;
import com.jotape.sisdic.CustomAdapters.TarefaAdapter;
import com.jotape.sisdic.CustomDialogs.MembroInfoDialog;
import com.jotape.sisdic.Obj.Membro;
import com.jotape.sisdic.Obj.Tarefa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//TODO na TarefaProfile: Mudar input do dialog -> designar responsável; de EditText para Lista dos membros.
public class MainActivity extends AppCompatActivity {

    ListView listView;

    //lista de membros.
    List<Membro> membersList = new ArrayList<Membro>();
    ArrayAdapter<Membro> membroAdapter;

    //lista de Tarefas.
    List<Tarefa> tarefasList  = new ArrayList<Tarefa>();
    ArrayAdapter<Tarefa> tarefaAdapter;

    //Firebase Database
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    //Tarefas Reference:
    DatabaseReference tarefasRef = ref.child("Tarefas");

    //Membros Reference:
    DatabaseReference membrosRef = ref.child("Membros");

    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_home: //TAREFAS EM PROGRESSO

                    listView.setAdapter(tarefaAdapter);
                    fetchTarefas(false);

                    //Abre o Profile da atividade
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent in =  new Intent(getApplicationContext(),TarefaProfiler.class);
                            in.putExtra("atv_name",tarefasList.get(i).getDescription());
                            in.putExtra("date",tarefasList.get(i).getDate());
                            in.putExtra("status",tarefasList.get(i).getStatus());
                            in.putExtra("person",tarefasList.get(i).getPerson());
                            startActivity(in);

                        }
                    });


                    return true;

                case R.id.navigation_dashboard: // TAREFAS COMPLETAS

                    listView.setAdapter(tarefaAdapter);
                    fetchTarefas(true);
                    return true;

                case R.id.navigation_notifications: //MEMBROS LIST

                    listView.setAdapter(membroAdapter);

                    fetchMembros();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Membro selected = membersList.get(i);

                            MembroInfoDialog dialog  = new MembroInfoDialog(MainActivity.this);
                            dialog.createDialog(selected);
                        }
                    });


                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Equipe UFBA IBRACON");

        listView = (ListView) findViewById(R.id.listView);

        //instance dos adapters
        membroAdapter = new MembroAdapter(this,membersList);
        tarefaAdapter = new TarefaAdapter(this,tarefasList);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        listView.setAdapter(tarefaAdapter);

        navigation.getMenu().performIdentifierAction(R.id.navigation_home,0);

    }
    @Override
    protected void onResume(){
        super.onResume();

        navigation.getMenu().performIdentifierAction(R.id.navigation_home,0);
        fetchTarefas(false);

    }


    //Menu dropdown handlers.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addMembroItem)
        {
            Intent i =  new Intent(getApplicationContext(),MembroForm.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.addTarefaItem)
        {
            Intent i =  new Intent(getApplicationContext(),TarefaForm.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchMembros(){
        membrosRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                membersList.clear();
                for(DataSnapshot v : children){

                    Membro m = v.getValue(Membro.class);
                    membersList.add(m);

                }

                listView.setAdapter(membroAdapter);
                membroAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                membersList.clear();
                for(DataSnapshot v : children){

                    Membro t = v.getValue(Membro.class);
                    membersList.add(t);

                }
                listView.setAdapter(membroAdapter);
                membroAdapter.notifyDataSetChanged();

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


    public void fetchTarefas(final boolean isCompleted){

        tarefasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                tarefasList.clear();
                for(DataSnapshot v : children){

                    Tarefa t = v.getValue(Tarefa.class);
                    if (isCompleted == t.isCompleted()){
                        tarefasList.add(t);
                    }

                }

                listView.setAdapter(tarefaAdapter);
                tarefaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                tarefasList.clear();
                for(DataSnapshot v : children){

                    Tarefa t = v.getValue(Tarefa.class);
                    if (isCompleted == t.isCompleted()){
                        tarefasList.add(t);
                    }


                }
                listView.setAdapter(tarefaAdapter);
                tarefaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                    tarefasList.clear();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
