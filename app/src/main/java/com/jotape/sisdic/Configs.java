package com.jotape.sisdic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jotape.sisdic.Modules.FirebaseWorker;

public class Configs extends AppCompatActivity {

    String VERSION = "0.98";
    TextView atual,latest,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configs);

        atual = (TextView) findViewById(R.id.installVersionTxt);
        latest = (TextView) findViewById(R.id.latestVersionTxt);
        link = (TextView) findViewById(R.id.link);

        link.setMovementMethod(LinkMovementMethod.getInstance());
        atual.setText(VERSION);

        FirebaseWorker.pushVersionToText(latest);


    }
}
