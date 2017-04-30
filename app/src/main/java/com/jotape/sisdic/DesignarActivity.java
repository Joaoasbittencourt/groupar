package com.jotape.sisdic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jotape.sisdic.CustomAdapters.MembroAdapter;
import com.jotape.sisdic.Modules.FirebaseWorker;
import com.jotape.sisdic.Obj.Membro;

import java.util.ArrayList;
import java.util.List;

public class DesignarActivity extends AppCompatActivity {

    //lista de membros.
    List<Membro> membersList = new ArrayList<Membro>();
    ArrayAdapter<Membro> membroAdapter;

    TextView tarefaTxt,memberSelection;
    Button submitButton;
    ListView listView;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designar_dialog);

        final String atv_name = getIntent().getExtras().get("tarefa").toString();

        tarefaTxt        = (TextView) findViewById(R.id.tarefaDisplayTxt);
        memberSelection  = (TextView) findViewById(R.id.membroSelection);
        submitButton     = (Button)   findViewById(R.id.submitBtn);
        listView         = (ListView) findViewById(R.id.membersListView);

        tarefaTxt.setText(atv_name);

        membroAdapter = new MembroAdapter(this,membersList);
        listView.setAdapter(membroAdapter);
        FirebaseWorker.populateMembrosList(membersList,listView,membroAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Membro selected = membersList.get(position);

                result += selected.getName() +"; ";
                Toast.makeText(DesignarActivity.this, selected.getName()+" foi incluído", Toast.LENGTH_SHORT).show();
                membersList.remove(position);
                membroAdapter.notifyDataSetChanged();
                memberSelection.setText(result);


            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseWorker.setTarefaPerson(atv_name,result);
                finish();

            }
        });
    }
}
