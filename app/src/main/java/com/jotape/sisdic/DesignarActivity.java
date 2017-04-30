package com.jotape.sisdic;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jotape.sisdic.Obj.Membro;

import java.util.ArrayList;
import java.util.List;

public class DesignarActivity extends AppCompatActivity {


    TextView tarefaTxt,memberSelection;
    Button submitButton;
    Context context;
    ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_profiler);


        ArrayList<String> unselected = new ArrayList<String>();
        ArrayList<String> selected   = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,selected);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.designar_dialog,null);

        tarefaTxt        = (TextView) view.findViewById(R.id.tarefaDisplayTxt);
        memberSelection  = (TextView) view.findViewById(R.id.membroSelection);
        submitButton     = (Button)   view.findViewById(R.id.submitBtn);
        listView         = (ListView) view.findViewById(R.id.membersListView);

        tarefaTxt.setText(getIntent().getExtras().get("tarefa").toString());

        listView.setAdapter(adapter);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
