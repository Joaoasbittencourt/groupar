package com.jotape.sisdic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jotape.sisdic.Modules.DateManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jotape.sisdic.Obj.Tarefa;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TarefaForm extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;



    Button addTarefa_btn,dateBtn;
    EditText descriptionEtxt;
    TextView limitDateTxt;

    Integer year_x,month_x,day_x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_form);

        database = FirebaseDatabase.getInstance();
        ref      = database.getReference();

        //Tarefas Reference:
        final DatabaseReference tarefasRef = ref.child("Tarefas");

        //Setup Date
        Calendar calendar =  Calendar.getInstance();
        year_x  = (Integer) calendar.get(Calendar.YEAR);
        month_x = (Integer)calendar.get(Calendar.MONTH);
        day_x   = (Integer) calendar.get(Calendar.DAY_OF_MONTH);

        limitDateTxt =  (TextView) findViewById(R.id.limitDateTxt);
        limitDateTxt.setText(DateManager.formatDateInts(day_x,month_x,year_x));

        dateBtn = (Button) findViewById(R.id.dateBtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });


        addTarefa_btn = (Button) findViewById(R.id.addTarefa_btn);
        addTarefa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                descriptionEtxt = (EditText) findViewById(R.id.descriptionEtxt);

                Tarefa t = new Tarefa(descriptionEtxt.getText().toString(),DateManager.formatDateInts(day_x,month_x,year_x),"N/A");

                try{

                    Map<String,Object> container = new HashMap<String,Object>();
                    container.put(t.getDescription(),t);

                    tarefasRef.child("Tarefas").updateChildren(container);
                    finish();

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TarefaForm.this, "O servidor da database não está disponínvel no momento" +
                            "tente novamente mais tarde.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }


    // Dialog setup

    public DatePickerDialog.OnDateSetListener datePicker =  new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month;
            day_x = dayOfMonth;

            Toast.makeText(TarefaForm.this, DateManager.formatDateInts(dayOfMonth,month,year), Toast.LENGTH_SHORT).show();
            limitDateTxt.setText(DateManager.formatDateInts(day_x,month_x,year_x));
        }
    };

    @Override
    public Dialog onCreateDialog(int id){
        return new DatePickerDialog(this,datePicker,year_x,month_x,day_x);
    }
}
