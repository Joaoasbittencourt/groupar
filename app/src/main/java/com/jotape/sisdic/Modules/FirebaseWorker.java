package com.jotape.sisdic.Modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jotape.sisdic.Obj.Membro;
import com.jotape.sisdic.Obj.Tarefa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by João Bittencourt on 29/04/2017.
 */

public class FirebaseWorker {


    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static void populateMembrosList(final List<Membro> membersList, final ListView listview, final Adapter ad, Context context){

        final ProgressDialog progressDialog =  new ProgressDialog(context);
        progressDialog.setMessage("Carregando dados...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        //Membros Reference:
        DatabaseReference membrosRef = ref.child("Membros");



        membrosRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                membersList.clear();
                for(DataSnapshot v : children){

                    Membro m = v.getValue(Membro.class);
                    membersList.add(m);

                }
                listview.setAdapter((ListAdapter) ad);

                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                membersList.clear();
                for(DataSnapshot v : children){

                    Membro t = v.getValue(Membro.class);
                    membersList.add(t);

                }
                listview.setAdapter((ListAdapter) ad);
                progressDialog.dismiss();
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


    public static void populateTarefasList(final boolean isCompleted, final List<Tarefa> tarefasList, final ListView listview, final Adapter ad,Context context){


        final ProgressDialog progressDialog =  new ProgressDialog(context);
        progressDialog.setMessage("Carregando dados...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        //Tarefas Reference:
        DatabaseReference tarefasRef = ref.child("Tarefas");

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
                    listview.setAdapter((ListAdapter) ad);
                }
                progressDialog.dismiss();

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
                listview.setAdapter((ListAdapter) ad);
                progressDialog.dismiss();
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


    public static void setTarefaPerson(String atvName, String persons){

        DatabaseReference atvRef = ref
                .child("Tarefas")
                .child("Tarefas")
                .child(atvName);

        try{

            atvRef.child("person").setValue(persons);
            atvRef.child("status").setValue("Em progresso");

        }catch (Exception e){
            System.out.print(e.toString());
        }

    }

    public static void pushVersionToText(final TextView text){


        ref.child("VERSION").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             text.setText((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




}
